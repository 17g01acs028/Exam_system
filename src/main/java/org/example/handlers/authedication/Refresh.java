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

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.example.handlers.authedication.Login.*;
import static org.example.handlers.authedication.Validate.validateToken;
import static org.example.libs.ConfigFileChecker.configFileChecker;
import static org.example.libs.Utils.responseFormatter;

public class Refresh {
    private static final String TOKEN_HEADER = "Authorization";
    public static void Handler(HttpServerExchange exchange) throws Exception {
        if (exchange.isInIoThread()) {
            exchange.dispatch(() -> {
                try {
                    processRequest(exchange);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return;
        }
        processRequest(exchange);
    }

    private static void processRequest(HttpServerExchange exchange) throws Exception {
        Response response = null;
        Response checker = configFileChecker("config");
        if(checker.getStatus()){
            Connection conn = null;
            try {
                FileReader connection = new FileReader("config/" + checker.getMessage());
                conn = connection.getConnection();

                String authHeader = exchange.getRequestHeaders().getFirst(TOKEN_HEADER);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); // Extract the token
                validateToken(exchange,token);

                String[] columns = {"user_id","username","token"};
                Response res = Select.select(conn,"auth_table",columns,null,"token = '"+token+"'",null,null,null);

                    System.out.println(res);
                if(res.getFieldValue("token") == null){
                    Utils.respondWithError(exchange,"Token invalidated",500);
                }

//                if(!token.equals(res.getFieldValue("token"))){
//                    Utils.respondWithError(exchange,"Someone logged in using the credentials",500);
//                }



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
                    String response_login = "{" + "\"message\": " + "\"" + "New access token generated successfully." + "\"" + "," +
                            "\"token\": " + "\"" + token_data + "\""+"}";

                    exchange.getResponseSender().send(response_login);
                }else{
                    Utils.respondWithError(exchange,"Error Occurred please try again",500);
                }

            } else {
                Utils.respondWithError(exchange,"Unauthorised",401);
            }
    }catch (SQLException es){
        Utils.respondWithError(exchange,"Internal server error",500);
    }finally {
        assert conn != null;
        conn.close();
    }
    }else{
        Utils.respondWithError(exchange, checker.getMessage(), 404);
        }

    }
}


