package org.example.queryBuilder;

import io.undertow.server.HttpServerExchange;
import org.example.libs.Response;
import org.example.libs.Utils;

import java.sql.*;
import java.util.*;
import static org.example.queryBuilder.QueryBuilder.querySelect;

public class Select {

    // Select all columns from a table
    public static Response select(Connection conn, String table) throws SQLException {
        return querySelect(conn, "SELECT * FROM " + table);
    }

    // Select specific columns from a table
    public static Response select(Connection conn, String table, String[] columns) throws SQLException {
        String columnList = String.join(", ", columns);
        return querySelect(conn, "SELECT " + columnList + " FROM " + table);
    }

    // Select with WHERE clause
    public static Response select(Connection conn, String table, String where) throws SQLException {
        return querySelect(conn, "SELECT * FROM " + table + " WHERE " + where);
    }

    // Select with WHERE clause and specific columns


    public static Response select(Connection conn, String table, String[] columns, String where) throws SQLException {

        String columnList="";
        if(columns == null){
            columnList = "*";
        }else{
            if(columns.length == 0){
                columnList = "*";
            }else{
                columnList = String.join(", ", columns);
            }
        }

        StringJoiner query = new StringJoiner(" ");
        query.add("SELECT").add(columnList).add("FROM").add(table);

        if (where != null && !where.isEmpty()) {
            query.add("WHERE").add(where);
        }

        return querySelect(conn, query.toString());
    }

    // Select with WHERE clause and GROUP BY
    public static Response select(Connection conn, String table, String where, String groupBy) throws SQLException {
        return querySelect(conn, "SELECT * FROM " + table + " WHERE " + where + " GROUP BY " + groupBy);
    }

    // Select with WHERE clause, GROUP BY, and specific columns
    public static Response select(Connection conn, String table, String[] columns, String where, String groupBy) throws SQLException {
        String columnList = String.join(", ", columns);
        return querySelect(conn, "SELECT " + columnList + " FROM " + table + " WHERE " + where + " GROUP BY " + groupBy);
    }


    // Select with LIMIT
    public static Response select(Connection conn, String table,String orderby ,int offset, int limit) throws SQLException {
        return querySelect(conn, "SELECT * FROM " + table +" ORDER BY "+orderby+ " LIMIT " + limit +" OFFSET "+offset);
    }

    public static Response select(Connection conn, String table, String[] columns,String[] except, String where,String order_by ,Integer offset, Integer limit){
  try{
        String columnList="";
        if(columns == null){
            columnList = "*";
        }else{
            if(columns.length == 0){
                columnList = "*";
            }else{
            columnList = String.join(", ", columns);
            }
        }

        String except_builder = "";
        if (except != null) {
            boolean inList = false;
            if(columnList.contains("*")){
             inList =true;
            }else{
                for (String s : except) {
                    if (columnList.contains(s)) {
                        inList = true;
                        break;
                    }
                }
            }

            if(inList){
            if(except.length != 0){
            except_builder = " ,\"\" as "+ String.join(", \"\" as ", except)+" ";
            }}

            inList=false;
        }
        System.out.println(except_builder);
        System.out.println(columnList);

        StringJoiner query = new StringJoiner(" ");
        query.add("SELECT").add(columnList).add(except_builder).add("FROM").add(table);

        if (where != null && !where.isEmpty()) {
            query.add("WHERE").add(where);
        }



        query.add("ORDER BY ").add(order_by);


        if (limit != null) {
            query.add("LIMIT").add(limit.toString());
        }

        if (offset != null) {
            query.add("OFFSET").add(offset.toString());
        }

        System.out.println(query.toString());
        return querySelect(conn, query.toString());
  }catch(Exception e){
      new Response(500,"Server error: "+e.getMessage());
  }

  return null;
    }


    public static Response select(Connection conn, String table, String[] columns, String joinType, String joinTable, String joinCondition) throws SQLException {
        String columnList = String.join(", ", columns);
        String query = "SELECT " + columnList + " FROM " + table + " " + joinType + " JOIN " + joinTable + " ON " + joinCondition;
        return querySelect(conn, query);
    }

    public static Response select(Connection conn, String table, String joinType, String joinTable, String joinCondition) throws SQLException {
        String query = "SELECT * FROM " + table + " " + joinType + " JOIN " + joinTable + " ON " + joinCondition;
        return querySelect(conn, query);
    }

    public static Response select(Connection conn, String[] columns, String[] tables, String[][] joinClauses, String where, String groupBy, String having, String orderBy, Integer offset, Integer limit) throws SQLException {
        StringJoiner query = new StringJoiner(" ");

        // SELECT clause
        if (columns != null && columns.length > 0) {
            String columnList = String.join(", ", columns);
            query.add("SELECT " + columnList);
        } else {
            query.add("SELECT *");
        }

        // FROM clause
        if (tables != null && tables.length > 0) {
            String tableList = String.join(", ", tables);
            query.add("FROM " + tableList);
        } else {
            // Handle case where no tables are provided
            throw new IllegalArgumentException("At least one table must be specified.");
        }

        // JOIN clauses
        if (joinClauses != null) {
            for (String[] joinClause : joinClauses) {
                if (joinClause != null && joinClause.length >= 3) {
                    String joinType = joinClause[0];
                    String joinTable = joinClause[1];
                    String joinCondition = joinClause[2];
                    if (joinType != null && !joinType.isEmpty() && joinTable != null && !joinTable.isEmpty() && joinCondition != null && !joinCondition.isEmpty()) {
                        query.add(joinType + " JOIN " + joinTable + " ON " + joinCondition);
                    }
                }
            }
        }

        // WHERE clause
        if (where != null && !where.isEmpty()) {
            query.add("WHERE " + where);
        }

        // GROUP BY clause
        if (groupBy != null && !groupBy.isEmpty()) {
            query.add("GROUP BY " + groupBy);
        }

        // HAVING clause
        if (having != null && !having.isEmpty()) {
            query.add("HAVING " + having);
        }

        // ORDER BY clause
        if (orderBy != null && !orderBy.isEmpty()) {
            query.add("ORDER BY " + orderBy);
        }

        // LIMIT clause
        if (offset != null) {
            query.add("OFFSET " + offset.toString());
        }

        // LIMIT clause
        if (limit != null) {
            query.add("LIMIT " + limit.toString());
        }

        return querySelect(conn, query.toString());
    }
}
