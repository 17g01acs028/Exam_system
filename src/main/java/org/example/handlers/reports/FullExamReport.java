package org.example.handlers.reports;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.example.libs.Response;
import org.example.libs.Utils;
import org.example.model.FileReader;
import org.example.reports.ExamReportGenerator;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import static org.example.libs.ConfigFileChecker.configFileChecker;

public class FullExamReport {

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
                       String e_id = exchange.getQueryParameters().get("e_id").getFirst();

                       if (e_id.isEmpty() ) {
                           Utils.respondWithError(exchange,"Id Not found error",404);
                       } else {
                       response =   ExamReportGenerator.genarateReport(conn,connection.databaseType,Integer.parseInt(e_id));
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
            }else if(response.getCode() == 200){

                String filePath = "StudentMeritReport.xlsx";
                exchange.getResponseHeaders()
                        .put(Headers.CONTENT_DISPOSITION, "attachment; filename=\"report.xlsx\"")
                        .put(Headers.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

                sendFile(exchange, filePath);
            }
        } catch (Exception e) {
            Utils.respondWithError(exchange, "Error processing request "+e.getMessage(), 500);
        }
    }
    private static void sendFile(HttpServerExchange exchange, String filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            OutputStream outputStream = exchange.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
