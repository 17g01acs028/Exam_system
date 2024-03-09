package org.example.handlers.authedication;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.util.Headers;
import org.example.libs.Response;
import org.example.libs.Utils;
import org.example.model.FileReader;
import org.example.queryBuilder.Insert;
import org.example.queryBuilder.Select;
import org.example.queryBuilder.Update;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.example.libs.ConfigFileChecker.configFileChecker;
import static org.example.libs.Utils.responseFormatter;

public class Login {

    public static void Handler(HttpServerExchange exchange) {
        if (exchange.isInIoThread()) {
            exchange.dispatch(() -> processRequest(exchange));
            return;
        }
        processRequest(exchange);
    }

    private static void processRequest(HttpServerExchange exchange) {
        String contentType = exchange.getRequestHeaders().getFirst(Headers.CONTENT_TYPE);

        if (contentType != null && contentType.contains("application/json")) {
            handleJsonRequest(exchange);
        } else if (contentType != null && contentType.contains("multipart/form-data")) {
            handleMultipartFormDataRequest(exchange);
        }else if(contentType == null){
            handleRequest(exchange);
        }else{
            Utils.respondWithError(exchange, "Error Invalid Content Type" , 500);
        }
    }

    private static void handleJsonRequest(HttpServerExchange exchange) {
        exchange.getRequestReceiver().receiveFullString((ex, message) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> values = mapper.readValue(message, Map.class);

                processAndRespond(exchange,values);
            } catch (Exception e) {
                Utils.respondWithError(exchange, "Error processing JSON request" + e.getMessage(), 500);
            }
        });
    }
    private static void handleRequest(HttpServerExchange exchange) {

            try {

                Map<String,String> values = new HashMap<String,String>();
                processAndRespond(exchange,values);
            } catch (Exception e) {
                Utils.respondWithError(exchange, "Error " + e.getMessage(), 500);
            }
    }
    private static void handleMultipartFormDataRequest(HttpServerExchange exchange) {
        exchange.startBlocking();
        FormDataParser parser = FormParserFactory.builder().build().createParser(exchange);
        try {
            FormData formData = parser.parseBlocking();
            Map<String, String> values = new HashMap<>();
            for (String data : formData) {
                FormData.FormValue formValue = formData.getFirst(data);
                if (!formValue.isFile()) {
                    values.put(data, formValue.getValue());
                }
            }
            processAndRespond(exchange,values);
        } catch (Exception e) {
            Utils.respondWithError(exchange, "Error processing form data "+ e.getMessage(), 500);
        }
    }

    private static void processAndRespond(HttpServerExchange exchange,  Map<String, String> values) {
        try {

            Response response = null;
            Response checker = configFileChecker("config");
            if(checker.getStatus()){
                Connection conn = null;
               try {
                   FileReader connection = new FileReader("config/" + checker.getMessage());
                   conn = connection.getConnection();

                   if(values.isEmpty()){
                       Utils.respondWithError(exchange,"Both Username and password are required",500);
                   }

                   if(!values.containsKey("username")){
                       Utils.respondWithError(exchange,"Username Required",500);
                   }

                   if(!values.containsKey("username")){
                       Utils.respondWithError(exchange,"Password Required",500);
                   }


                   String[] columns = {"user_id","username","password","login_attempts"};
                   String username =  values.get("username");
                   Response res = Select.select(conn,"auth_table",columns,null,"username = '"+username+"'",null,null,null);
                   assert res != null;

                   if(res.getFieldValue("username") == null){
                       Utils.respondWithError(exchange,"User does not exists in our system.",500);
                   }
                   if(Integer.parseInt(res.getFieldValue("login_attempts")) > 2){
                       Utils.respondWithError(exchange,"Sorry your account is locked please contact your administrator.",500);
                   }

                  if(Utils.encrypt(values.get("password"),Utils.key).equalsIgnoreCase(res.getFieldValue("password"))){

                      String token_data = generateToken(res.getFieldValue("username"));
                      LocalDateTime dateTime = LocalDateTime.now();
                      String token_create = formatDateTime(dateTime);
                      String token_expire = addSeconds(dateTime,300);

                      Map<String,String> auth_values = new HashMap<String,String>();
                      auth_values.put("token",token_data);
                      auth_values.put("token_created_at",token_create);
                      auth_values.put("token_expires_at",token_expire);
                      auth_values.put("login_attempts","0");

                      Response update = Update.updateSingleRow(conn,"auth_table",auth_values,res.getFieldValue("user_id"));

                      if(update.getCode() == 200){
                          exchange.setStatusCode(200);
                          exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                          String response_login = "{" + "\"message\": " + "\"" + "Logged in successfully" + "\"" + "," +
                                  "\"token\": " + "\"" + token_data + "\""+"}";

                          exchange.getResponseSender().send(response_login);
                      }else{
                          Utils.respondWithError(exchange,"Error Occurred please try again",500);
                      }

                  }else{
                      Utils.respondWithError(exchange,"Incorrect user password.",500);
                  }

                   System.out.println(res.getFieldValue("username"));

               }catch (SQLException es){
                  Utils.respondWithError(exchange,"Internal server error",500);
               }finally {
                   assert conn != null;
                   conn.close();
               }
            }else{
                Utils.respondWithError(exchange, checker.getMessage(), 404);
            }
         } catch (Exception e) {
            Utils.respondWithError(exchange, "Error processing request "+e.getMessage(), 500);
        }


    }

    public static String addSeconds(LocalDateTime dateTime, int seconds) {
        return formatDateTime(dateTime.plusSeconds(seconds));
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    public static String generateToken(String username) throws Exception {
        byte[] randomBytes = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);

        // Combine username and random bytes
        String tokenData = username + System.currentTimeMillis() + new String(randomBytes);
        return  Utils.encrypt(tokenData,Utils.key);
    }
}
