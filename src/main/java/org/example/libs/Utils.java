package org.example.libs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String key = "beadc627d00ec777340bf6f06ece360fe1762e8b4408504516afd194dc303c77";

    public static void respondWithError(HttpServerExchange exchange, String errorMessage, int statusCode) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.setStatusCode(statusCode);
        exchange.getResponseSender().send("{\"message\": \""+errorMessage+"\"}");
    }

  public static String responseFormatter(JSONArray jsonArray){

        if (jsonArray.length() == 1) {
            String originalStr = jsonArray.toString();
            String modifiedStr = originalStr.substring(1, originalStr.length() - 1);
            return modifiedStr;
        } else if (jsonArray.length() == 0) {
            return "{\"Message: No record Found\"}";
        }

        return jsonArray.toString();
  }
    public static boolean isExpired(String expiryDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return currentDateTime.isAfter(parseDateTime(expiryDateTime));
    }
    public static LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    public static String encrypt(String data, String key) throws Exception {
        byte[] keyBytes = hexStringToByteArray(key);
        if (keyBytes.length != 32) {
            throw new IllegalArgumentException("Key must be 32 bytes long");
        }

        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String decrypt(String encryptedData, String key) throws Exception {
        byte[] keyBytes = hexStringToByteArray(key);
        if (keyBytes.length != 32) {
            throw new IllegalArgumentException("Key must be 32 bytes long");
        }

        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decrypted = cipher.doFinal(encryptedBytes);

        return new String(decrypted);
    }

  public static String[] splitColumns(HttpServerExchange exchange,String columns){
columns = columns.trim();
  if(!isValidFormat(columns)){
     Utils.respondWithError(exchange,"Please check the accepted columns input format, '"+columns+"' is wrong.",400);
  }

      var withoutParentheses = columns.substring(1, columns.length() - 1);

      return withoutParentheses.split(",");
  }

    public static boolean isValidFormat(String s) {
         s = s.trim();
        // Define a regular expression pattern for the required format
        String pattern = "^\\(([^,]+,)+[^,]+\\)$";

        // Compile the regular expression
        Pattern regex = Pattern.compile(pattern);

        // Check if the string matches the pattern
        Matcher matcher = regex.matcher(s);
        return matcher.matches();
    }
    public static String responseFormatter(JSONArray jsonArray,int code, int page,int pages, int pageSize, long totalRecord){

        if (jsonArray.length() == 1) {
            String originalStr = jsonArray.toString();
            String modifiedStr = originalStr.substring(1, originalStr.length() - 1);
            return modifiedStr;
        } else if (jsonArray.length() == 0) {
            return "{\"Message: No record Found\"}";
        }

        return "{" +
                "\"code\": " + code + "," +
                "\"page\": " + page + "," +
                "\"pages\": " + pages + "," +
                "\"page size\": " + pageSize + "," +
                "\"total record\": " + totalRecord + "," +
                "\"data\": " + jsonArray +
                "}";
    }

    public static String convertJsonArrayToXml(JSONArray jsonArray, String parent, String child) {

//        StringBuilder xmlBuilder = new StringBuilder("");
//        if (jsonArray.length() == 1) {
//            Object obj = jsonArray.get(0);
//            if (obj instanceof JSONObject) {
//                xmlBuilder.append("<").append(child).append(">");
//                xmlBuilder.append(XML.toString((JSONObject) obj));
//                xmlBuilder.append("</").append(child).append(">\n");
//            }
//
//            return  xmlBuilder.toString();
//        } else if (jsonArray.length() == 0) {
//            return "<" + parent + ">No record</" + parent + ">";
//        }
//
//              xmlBuilder = new StringBuilder("<"+parent+">");
//        for (int i = 0; i < jsonArray.length(); i++) {
//            Object obj = jsonArray.get(i);
//            if (obj instanceof JSONObject) {
//                xmlBuilder.append("<").append(child).append(">");
//                xmlBuilder.append(XML.toString((JSONObject) obj));
//                xmlBuilder.append("</").append(child).append(">\n");
//            }
//
//        }
//        xmlBuilder.append("</"+parent+">");
//        return xmlBuilder.toString();

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(jsonArray);
        } catch (Exception e) {
            // Handle any potential exceptions during JSON conversion
            return "Error converting to JSON: " + e.getMessage();
        }


    }



}
