package org.example.handlers.subjects;

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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.example.libs.ConfigFileChecker.configFileChecker;
import static org.example.libs.Utils.responseFormatter;

public class CreateSubject {

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
                   response = Insert.insertSingleRow(conn,"subject", values);
               }catch (SQLException es){
                  Utils.respondWithError(exchange,"Internal server error",500);
               }finally {
                   assert conn != null;
                   conn.close();
               }
            }else{
                Utils.respondWithError(exchange, checker.getMessage(), 404);
            }
            assert response != null;

            if(response.getCode() == 500){
                Utils.respondWithError(exchange, response.getMessage(), 500);
            }else{
                exchange.setStatusCode(201);
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                exchange.getResponseSender().send(responseFormatter(response.getResponse()));}
        } catch (Exception e) {
            Utils.respondWithError(exchange, "Error processing request "+e.getMessage(), 500);
        }
    }


}
