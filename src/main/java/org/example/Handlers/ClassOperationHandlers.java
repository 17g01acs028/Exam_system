package org.example.Handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.server.handlers.form.FormParserFactory;
import io.undertow.util.Headers;
import org.example.libs.Response;
import org.example.libs.Utils;
import org.example.controllers.Class;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class ClassOperationHandlers {

    public static void classHandler(HttpServerExchange exchange, Connection conn, String operation_type) {
        if (exchange.isInIoThread()) {
            exchange.dispatch(() -> processRequest(exchange, conn, operation_type));
            return;
        }
        processRequest(exchange, conn, operation_type);
    }

    private static void processRequest(HttpServerExchange exchange, Connection conn, String operationType) {
        String contentType = exchange.getRequestHeaders().getFirst(Headers.CONTENT_TYPE);

        if (contentType != null && contentType.contains("application/json")) {
            handleJsonRequest(exchange, conn, operationType);
        } else if (contentType != null && contentType.contains("multipart/form-data")) {
            handleMultipartFormDataRequest(exchange, conn, operationType);
        }else if(contentType == null){
            handleRequest(exchange,conn,operationType);
        }else{
            Utils.respondWithError(exchange, "Error Invalid Content Type" , 500);
        }
    }

    private static void handleJsonRequest(HttpServerExchange exchange, Connection conn, String operationType) {
        exchange.getRequestReceiver().receiveFullString((ex, message) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> values = mapper.readValue(message, Map.class);

                processAndRespond(exchange, conn, values,operationType);
            } catch (Exception e) {
                Utils.respondWithError(exchange, "Error processing JSON request" + e.getMessage(), 500);
            }
        });
    }
    private static void handleRequest(HttpServerExchange exchange, Connection conn, String operationType) {

            try {

                Map<String,String> values = new HashMap<String,String>();
                processAndRespond(exchange, conn, values,operationType);
            } catch (Exception e) {
                Utils.respondWithError(exchange, "Error " + e.getMessage(), 500);
            }
    }
    private static void handleMultipartFormDataRequest(HttpServerExchange exchange, Connection conn, String operationType) {
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
            // Here you can use operationType to differentiate between operations
            processAndRespond(exchange, conn, values,operationType);
        } catch (Exception e) {
            Utils.respondWithError(exchange, "Error processing form data "+ e.getMessage(), 500);
        }
    }

    private static void processAndRespond(HttpServerExchange exchange, Connection conn, Map<String, String> values, String operation_type) {
        try {

            Response response = null;
            if("add".equalsIgnoreCase(operation_type)){
                response = Class.addClass(conn, values);
            }else if("select".equalsIgnoreCase(operation_type)){
                if(exchange.getQueryParameters().isEmpty()){
                    response = Class.Find(conn);
                }else {
                    String id = exchange.getQueryParameters().get("id").getFirst();
                    if (id.isEmpty()) {
                        response = Class.Find(conn);
                    } else {
                        response = Class.FindById(conn, Integer.parseInt(id));
                    }
                }

            }else if("update".equalsIgnoreCase(operation_type)){
                if(exchange.getQueryParameters().isEmpty()){
                    Utils.respondWithError(exchange, "Error ID not ", 500);
                }else{
                var id = exchange.getQueryParameters().get("id");
                if(id.isEmpty()){
                    Utils.respondWithError(exchange, "Error ID not ", 500);
                }else{
                response = Class.updateClass(conn,values,id.getFirst());
                }}
            }
            assert response != null;

            if(response.getCode() == 500){
                Utils.respondWithError(exchange, response.getMessage(), 500);
            }else{

            String xmlResponse = Utils.convertJsonArrayToXml(response.getResponse(),"classes", "class");
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/xml");
            exchange.getResponseSender().send(xmlResponse);}
        } catch (Exception e) {
            Utils.respondWithError(exchange, "Error processing request "+e.getMessage(), 500);
        }
    }


}
