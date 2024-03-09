package org.example.handlers.authedication;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import org.example.libs.Response;
import org.example.libs.Utils;
import org.example.model.FileReader;
import org.example.queryBuilder.Select;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.example.libs.ConfigFileChecker.configFileChecker;
import static org.example.libs.Utils.responseFormatter;

public class Validate {
    private static final String TOKEN_HEADER = "Authorization";

    public static HttpHandler authenticationMiddleware(HttpHandler next, String... allowedRoles) {
        return exchange -> {
            String authHeader = exchange.getRequestHeaders().getFirst(TOKEN_HEADER);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); // Extract the token
                validateToken(exchange,token,allowedRoles);
                next.handleRequest(exchange);
            } else {
                Utils.respondWithError(exchange,"Unauthorised",401);
            }

        };
    }

    static void validateToken(HttpServerExchange exchange, String token,String... allowedRolesList) throws SQLException {
        Response checker = configFileChecker("config");
        if(checker.getStatus()){
            Connection conn = null;
            try {
                FileReader connection = new FileReader("config/" + checker.getMessage());
                conn = connection.getConnection();

                String[] columns = {"token","token_created_at","token_expires_at","role"};

                System.out.println("start and token from frontend is "+token);
                Response res = Select.select(conn,"auth_table",columns,null,"token = '"+token+"'",null,null,null);
                assert res != null;
                System.out.println("This is the responses"+res);

                if(res.getFieldValue("token") == null){
                    Utils.respondWithError(exchange,"Invalid Token, please log in again",500);
                }

                if(!token.equals(res.getFieldValue("token"))){
                    Utils.respondWithError(exchange,"Invalid Token, please log in again",500);
                }

               if(Utils.isExpired(res.getFieldValue("token_expires_at"))){
                   Utils.respondWithError(exchange,"Token expired",500);
               }

               boolean allowed =false;
                for (String role : allowedRolesList) {
                    if(role.equals(res.getFieldValue("role"))){
                        allowed = true;
                        break;
                    }
                }

                if(!allowed){
                    Utils.respondWithError(exchange,"Forbidden: Insufficient privileges",403);
                }

            }catch (Exception es){
                Utils.respondWithError(exchange,"Internal server error",500);
            }finally {
                assert conn != null;
                conn.close();
            }
        }else{
            Utils.respondWithError(exchange, checker.getMessage(), 404);
        }

    }



}
