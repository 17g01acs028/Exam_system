package org.example.handlers.responses;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.example.libs.Response;
import org.example.libs.Utils;
import org.example.model.FileReader;
import org.example.queryBuilder.Select;

import java.sql.Connection;
import java.sql.SQLException;

import static org.example.libs.ConfigFileChecker.configFileChecker;
import static org.example.libs.Utils.responseFormatter;

public class GetResponse {

    public static void Handler(HttpServerExchange exchange) {
        if (exchange.isInIoThread()) {
            exchange.dispatch(() -> processAndRespond(exchange));
            return;
        }
        processAndRespond(exchange);
    }


    private static void processAndRespond(HttpServerExchange exchange) {
        try {

            Response response = null;
            Response checker = configFileChecker("config");
            if(checker.getStatus()){
                Connection conn = null;
               try {
                   FileReader connection = new FileReader("config/" + checker.getMessage());
                   conn = connection.getConnection();
                   if (exchange.getQueryParameters().isEmpty()) {
                       response = Select.select(conn, "responses");
                   } else {
                       String id = exchange.getQueryParameters().get("id").getFirst();
                       if (id.isEmpty()) {
                           response = Select.select(conn, "responses");
                       } else {
                           response = Select.select(conn, "responses","response_id ="+id);
                       }
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
            assert response != null;

            if(response.getCode() == 500){
                Utils.respondWithError(exchange, response.getMessage(), 500);
            }else{
                if (response.getResponse().isEmpty()) {
                    Utils.respondWithError(exchange, "Record Not found", 404);
                }
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                exchange.getResponseSender().send(responseFormatter(response.getResponse()));}
        } catch (Exception e) {
            Utils.respondWithError(exchange, "Error processing request "+e.getMessage(), 500);
        }
    }


}
