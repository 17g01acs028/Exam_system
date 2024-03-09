package org.example.handlers.students;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.example.libs.Response;
import org.example.libs.Utils;
import org.example.model.FileReader;
import org.example.queryBuilder.Select;

import java.sql.Connection;
import java.sql.SQLException;

import static org.example.handlers.base.ProcessResponses.processAndResponses;
import static org.example.libs.ConfigFileChecker.configFileChecker;
import static org.example.libs.Utils.responseFormatter;

public class GetStudents {

    public static void Handler(HttpServerExchange exchange) {
        if (exchange.isInIoThread()) {
            exchange.dispatch(() -> processAndRespond(exchange));
            return;
        }
        processAndRespond(exchange);
    }

    private static void processAndRespond(HttpServerExchange exchange) {
        try {
            Response response = processAndResponses(exchange,"student","student_id");
            assert response != null;

            if(response.getCode() == 500){
                Utils.respondWithError(exchange, response.getMessage(), 500);
            }else{
                if (response.getResponse().isEmpty()) {
                    Utils.respondWithError(exchange, "Record Not found", 404);
                }
           // String xmlResponse = Utils.convertJsonArrayToXml(response.getResponse(),"students", "student");
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                exchange.getResponseSender().send(responseFormatter(response.getResponse(),200,response.getPage(),response.getPages(), response.getPageSize(), response.getTotalRecord()));}
        } catch (Exception e) {
            Utils.respondWithError(exchange, "Error processing request "+e.getMessage(), 500);
        }
    }


}
