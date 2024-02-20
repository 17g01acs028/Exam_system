package org.example.libs;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class Utils {

    public static void respondWithError(HttpServerExchange exchange, String errorMessage, int statusCode) {
        exchange.setStatusCode(statusCode);
        exchange.getResponseSender().send(errorMessage);
    }

    public static String convertJsonArrayToXml(JSONArray jsonArray, String parent, String child) {

        StringBuilder xmlBuilder = new StringBuilder("");
        if (jsonArray.length() == 1) {
            Object obj = jsonArray.get(0);
            if (obj instanceof JSONObject) {
                xmlBuilder.append("<").append(child).append(">");
                xmlBuilder.append(XML.toString((JSONObject) obj));
                xmlBuilder.append("</").append(child).append(">\n");
            }

            return  xmlBuilder.toString();
        } else if (jsonArray.length() == 0) {
            return "<" + parent + ">No record</" + parent + ">";
        }

              xmlBuilder = new StringBuilder("<"+parent+">");
        for (int i = 0; i < jsonArray.length(); i++) {
            Object obj = jsonArray.get(i);
            if (obj instanceof JSONObject) {
                xmlBuilder.append("<").append(child).append(">");
                xmlBuilder.append(XML.toString((JSONObject) obj));
                xmlBuilder.append("</").append(child).append(">\n");
            }

        }
        xmlBuilder.append("</"+parent+">");
        return xmlBuilder.toString();
    }
}
