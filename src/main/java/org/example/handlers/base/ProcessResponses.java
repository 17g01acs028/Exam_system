package org.example.handlers.base;

import io.undertow.server.HttpServerExchange;
import org.example.libs.Response;
import org.example.libs.Utils;
import org.example.model.FileReader;
import org.example.queryBuilder.Select;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Deque;
import java.util.Map;

import static org.example.libs.ConfigFileChecker.configFileChecker;
import static org.example.libs.Utils.responseFormatter;

public class ProcessResponses {
    public static Response processAndResponses(HttpServerExchange exchange, String table, String primary_key) {
        try {
            Response response = null;
            Response checker = configFileChecker("config");
            if(checker.getStatus()){
                Connection conn = null;
                try {
                    int max_limit = 100;
                    int page = 1;
                    int pageSize = 10;
                    int pages =1;
                    long total_record=0;
                    String[] columns = new String[0];

                    StringBuilder where = new StringBuilder();

                    System.out.println("Started here");
                    FileReader connection = new FileReader("config/" + checker.getMessage());
                    conn = connection.getConnection();
                    System.out.println("Started z");
                    String[] column = {"count(*)"};
                    Response count = Select.select(connection.getConnection(),table,column,null,where.toString(),null,null,null);
                    total_record = Long.parseLong(count.getFieldValue("count(*)"));
                    System.out.println("here");
                    if (total_record > 0 && pageSize > 0) {
                        pages =(int) (total_record + pageSize - 1) / pageSize;
                    } else {
                        pages = 0;
                    }

                    if (!exchange.getQueryParameters().isEmpty()) {
                        Map<String, Deque<String>> queryParams = exchange.getQueryParameters();
                        for (Map.Entry<String, Deque<String>> paramEntry : queryParams.entrySet()) {
                            String paramName = paramEntry.getKey();
                            Deque<String> paramValues = paramEntry.getValue();

                            // Access each value:
                            for (String value : paramValues) {
                                if("page".equalsIgnoreCase(paramName) ){
                                    try {
                                        page = Integer.parseInt(value);
                                    }catch(Exception e){
                                        Utils.respondWithError(exchange,"Please provide a valid page number. This '"+value+"' is not a valid page number.",400);
                                    }

                                }

                                if("pageSize".equalsIgnoreCase(paramName)){
                                    try {
                                        pageSize = Integer.parseInt(value);
                                    }catch(Exception e){
                                        Utils.respondWithError(exchange,"Please provide a valid page size. This '" + value + "' is not a valid page size",400);
                                    }
                                }

                                if("columns".equalsIgnoreCase(paramName)){
                                    columns = Utils.splitColumns(exchange,value);
                                    if(columns.length < 1){
                                        columns = null;
                                    }
                                }

                                if("page".equalsIgnoreCase(paramName) || "pageSize".equalsIgnoreCase(paramName) || "filter".equalsIgnoreCase(paramName) || "columns".equalsIgnoreCase(paramName)){

                                }else{
                                    Utils.respondWithError(exchange,"Please check the accepted query param format, Param '" + paramName + "' is Not allowed!",400);
                                }
                            }

                        }

                        if(pageSize > max_limit){
                            Utils.respondWithError(exchange,"Allowed page size limit is '" + max_limit+"'",400);
                        }

                        if(page < 1){
                            Utils.respondWithError(exchange,"Allowed page number is from '" + 1 + "'" ,400);
                        }
                    }

                    String[] except = {"password"};
                    response = Select.select(conn, table,columns,except, where.toString(),primary_key,((page-1)*pageSize),pageSize);

                    return new Response(200,response.getResponse(),pages,page,total_record,pageSize);

                }catch (SQLException es){
                    Utils.respondWithError(exchange,"Internal server error",500);
                }finally {
                    assert conn != null;
                    conn.close();
                }
            }else{
                Utils.respondWithError(exchange, checker.getMessage(), 404);
            }

         } catch (SQLException z){
             Utils.respondWithError(exchange, "Error processing request "+z.getMessage(), 500);
         } catch (Exception e) {
            Utils.respondWithError(exchange, "Error processing request "+e.getMessage(), 500);
        }

        return null;
    }
}
