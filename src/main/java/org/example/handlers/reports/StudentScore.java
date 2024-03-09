package org.example.handlers.reports;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.example.libs.Response;
import org.example.libs.Utils;
import org.example.model.FileReader;
import org.example.reports.ExamReport;

import java.sql.Connection;
import java.sql.SQLException;

import static org.example.libs.ConfigFileChecker.configFileChecker;
import static org.example.libs.Utils.responseFormatter;

public class StudentScore {

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
                       Utils.respondWithError(exchange,"Id Not found error",404);
                   } else {
                       String s_id = exchange.getQueryParameters().get("s_id").getFirst();
                       String e_id = exchange.getQueryParameters().get("e_id").getFirst();
                       if (s_id.isEmpty() || e_id.isEmpty()) {
                           Utils.respondWithError(exchange,"Id Not found error",404);
                       } else {
                           response = ExamReport.studentScore(conn,Integer.parseInt(e_id),Integer.parseInt(s_id));
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
