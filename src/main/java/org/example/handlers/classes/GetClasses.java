package org.example.handlers.classes;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.util.Headers;
import org.example.libs.Response;
import org.example.libs.Utils;
import org.example.model.FileReader;
import org.example.queryBuilder.Select;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static org.example.handlers.base.ProcessResponses.processAndResponses;
import static org.example.libs.ConfigFileChecker.configFileChecker;
import static org.example.libs.Utils.responseFormatter;

public class GetClasses {

    public static void Handler(HttpServerExchange exchange) {
        if (exchange.isInIoThread()) {
            exchange.dispatch(() -> handleRequest(exchange));
            return;
        }
        handleRequest(exchange);
    }

    private static void handleRequest(HttpServerExchange exchange) {

            try {

                Map<String,String> values = new HashMap<String,String>();
                processAndRespond(exchange);
            } catch (Exception e) {
                Utils.respondWithError(exchange, "Error " + e.getMessage(), 500);
            }
    }
    private static void processAndRespond(HttpServerExchange exchange) {
        try {
           Response response = processAndResponses(exchange,"class","class_id");
           assert response != null;

            if(response.getCode() == 500){
                Utils.respondWithError(exchange, response.getMessage(), 500);
            }else{
                if (response.getResponse().isEmpty()) {
                    Utils.respondWithError(exchange, "Record Not found", 404);
                }

               // String xmlResponse = Utils.convertJsonArrayToXml(response.getResponse(),"classes", "class");
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                exchange.getResponseSender().send(responseFormatter(response.getResponse()));}
        } catch (Exception e) {
            Utils.respondWithError(exchange, "Error processing request "+e.getMessage(), 500);
        }
    }


}
