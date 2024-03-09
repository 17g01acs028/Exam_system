package org.example.handlers.base;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;

public class FallBack implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) {

        exchange.setStatusCode(StatusCodes.OK);

        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/json");
        exchange.getResponseSender().send("{ \"message: \" \"URI "+exchange.getRequestURI()+" not found on server \"}");
    }
}