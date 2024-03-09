package org.example;

import org.example.libs.Utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WhereClauseGenerator {

    // Mapping of condition keys to SQL symbols
    private static final Map<String, String> sqlConditions = new HashMap<>();

    static {
        sqlConditions.put("in", "IN");
        sqlConditions.put("inn", "IS NOT NULL");
        sqlConditions.put("eq", "=");
        sqlConditions.put("nq", "!=");
        sqlConditions.put("gt", ">");
        sqlConditions.put("gte", ">=");
        sqlConditions.put("lt", "<");
        sqlConditions.put("lte", "<=");
        sqlConditions.put("AND", "AND");
        sqlConditions.put("OR", "OR");
        sqlConditions.put("NOT", "NOT");
        sqlConditions.put("bt", "BETWEEN");
        sqlConditions.put("sw", "LIKE");
        sqlConditions.put("ew", "LIKE");
        sqlConditions.put("lm", "LIKE");
    }

    public static String generateWhereClause(String filter) {
        filter = filter.replaceAll("'","");
        StringBuilder whereClause = new StringBuilder();
        String[] conditions = filter.split("(?=AND)|(?=OR)");
        for(int i = 0; i < conditions.length; i++) {
            String condition = conditions[i];
            String[] parts = condition.split(":");
            if (parts.length != 3) {
                // Incorrect syntax, return error
                return "Incorrect syntax in filter condition: " + condition;
            }
            String field = parts[0];
            String operatorKey = parts[1];
            String value = parts[2];
            String sqlOperator = sqlConditions.get(operatorKey);
            if (sqlOperator == null) {
                return "Unknown operator in filter condition: " + condition;
            }

            if("bt".equalsIgnoreCase(operatorKey)){
                String[] check = value.split(",");
                if(check.length != 2){
                    return "Incorrect syntax for BETWEEN condition: " + condition;
                }
                value = value.replaceAll(",","' AND '");
            }

            if("sw".equalsIgnoreCase(operatorKey)){
                 value = " %"+value;
            }

           if("in".equalsIgnoreCase(operatorKey)){
              if(!Utils.isValidFormat(value)){
                  return "Incorrect syntax for In condition: " + condition;
              }
               whereClause.append(field)
                       .append(" ")
                       .append(sqlOperator)
                       .append(" ")
                       .append(value);
              }else{
               whereClause.append(field)
                       .append(" ")
                       .append(sqlOperator)
                       .append(" ")
                       .append(sanitizeSQLValue(value));
           }




        }
      String final_where = whereClause.toString().replaceAll("\\|"," ");
        if(!areParenthesesBalanced(final_where)){
            return "Please check all ( and ) brackets if they match  :" +final_where;
        }
        return final_where;
    }

    public static String sanitizeSQLValue(String value) {
        // Remove leading and trailing spaces
        String sanitizedValue = value.trim();

        String symbol = "";
        if(sanitizedValue.contains("|") || sanitizedValue.contains("(") || sanitizedValue.contains(")") || sanitizedValue.contains(" AND ") ){
            int split1 = sanitizedValue.indexOf("|");
            int split2 = sanitizedValue.indexOf("(");
            int split3 = sanitizedValue.indexOf(")");

            HashMap<String, Integer> values = new HashMap<String, Integer>();
            values.put("|",split1);
            values.put("(",split2);
            values.put(")",split3);

            // Check if the value is less than 0
            // Remove the entry from the HashMap
            values.entrySet().removeIf(entry -> entry.getValue() < 0);
            Optional<Map.Entry<String,Integer>> min= values.entrySet().stream().min(Map.Entry.comparingByValue());
            symbol = min.get().getKey();

        }

        String[] parts = sanitizedValue.split(Pattern.quote(symbol), 2);

        // If parts has two elements, add single quotes around the first part
        if (parts.length == 2) {
            parts[0] = "'" + parts[0] + "'";
            sanitizedValue = parts[0] + symbol + parts[1];
        }



        return sanitizedValue;
    }
    public static boolean areParenthesesBalanced(String expression) {
        // Regular expression to match parentheses
        String regex = "\\(|\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expression);

        int count = 0;
        while (matcher.find()) {
            if (matcher.group().equals("(")) {
                count++;
            } else if (matcher.group().equals(")")) {
                count--;
                if (count < 0) {
                    // More closing parentheses than opening ones
                    return false;
                }
            }
        }
        return count == 0;
    }
    public static void main(String[] args) {
        // Example usage
        String filter = "((gender:eq:M|AND|date_of_birth:bt:2005-01-01,2005-04004)|OR|(gender:eq:F|AND|date_of_birth:bt:2005-08-08,2005-10-10))|AND|department:in:('HR','Finance')";
        String whereClause = generateWhereClause(filter);
        if (whereClause.startsWith("Incorrect syntax") || whereClause.startsWith("Unknown operator")) {
            System.out.println("Error: " + whereClause);
        } else {
            System.out.println("Generated WHERE clause: " + whereClause);
        }



    }


}

