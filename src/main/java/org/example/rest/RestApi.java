package org.example.rest;

import io.undertow.Handlers;
import io.undertow.Undertow;
import org.example.libs.Response;
import org.example.model.FileReader;
import io.undertow.server.handlers.accesslog.AccessLogHandler;
import io.undertow.server.handlers.accesslog.DefaultAccessLogReceiver;

import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.example.libs.ConfigFileChecker.configFileChecker;

public class RestApi {
    public static void start(){
        try {
            final ExecutorService executor = Executors.newSingleThreadExecutor();
            DefaultAccessLogReceiver.Builder builder = DefaultAccessLogReceiver.builder().setLogWriteExecutor(executor)
                    .setOutputDirectory(Paths.get("logs/"))
                    .setLogBaseName("access-log.")
                    .setLogNameSuffix("log")
                    .setRotate(true);

            Response checker = configFileChecker("config");
            if(checker.getStatus()){
                FileReader config = new FileReader("config/"+checker.getMessage());
                Undertow server =  Undertow.builder().addHttpListener(config.getAppPort(), config.getAppIp())
                        .setHandler(
                                new AccessLogHandler(
                                Handlers.path().addPrefixPath("/api",org.example.routes.Main.Routes()
                                ),builder.build(),"combined", RestApi.class.getClassLoader())
                        )
                        .build();
                server.start();
                System.out.println("Server started on :"+config.getAppIp()+":"+config.getAppPort());
            }else{
                System.out.println(checker.getMessage());
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }




}
