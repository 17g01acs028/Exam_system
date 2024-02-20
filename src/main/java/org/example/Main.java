package org.example;

import org.example.Handlers.ClassOperationHandlers;
import org.example.Handlers.PupilOperationHandlers;
import org.example.Handlers.ResponseOperationHandlers;
import org.example.Handlers.ScheduleOperationHandlers;
import org.example.Handlers.OptionsOperationHandlers;
import org.example.Handlers.TeacherOperationHandlers;
import org.example.Handlers.SubjectOperationHandlers;
import org.example.Handlers.ExamOperationHandlers;
import org.example.Handlers.ReportOperationHandlers;
import org.example.Handlers.QuestionsOperationHandlers;
import org.example.libs.Response;
import org.example.libs.Utils;
import org.example.model.DatabaseConnection;
import java.sql.Connection;
import static org.example.libs.ConfigFileChecker.configFileChecker;
import io.undertow.Undertow;


public class Main{
    public static  String fileName;
    private static final int port = 9000;
    public static void main(String[] args){
        try {
            Response checker = configFileChecker("config",fileName);
            if(checker.getStatus()){
                DatabaseConnection connection = new DatabaseConnection("config/"+checker.getMessage());
                Connection conn = connection.getConnection();
                Undertow server = Undertow.builder()
                        .addHttpListener(port, "localhost")
                        .setHandler(exchange -> {
                            String path = exchange.getRequestPath();
                            switch (path) {
                                case "/api/class/add":
                                    if ("POST".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        ClassOperationHandlers.classHandler(exchange, conn,"add");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/class/all":
                                case "/api/class/findOne":
                                case "/api/class/findById":
                                    if ("GET".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        ClassOperationHandlers.classHandler(exchange, conn,"select");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/class/update":
                                    if ("PUT".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        ClassOperationHandlers.classHandler(exchange, conn,"update");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/student/add":
                                    if ("POST".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        PupilOperationHandlers.pupilHandler(exchange, conn,"add");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/student/all":
                                case "/api/student/findOne":
                                case "/api/student/findById":
                                    if ("GET".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        PupilOperationHandlers.pupilHandler(exchange, conn,"select");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/student/update":
                                    if ("PUT".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        PupilOperationHandlers.pupilHandler(exchange, conn,"update");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/schedule/add":
                                    if ("POST".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        ScheduleOperationHandlers.scheduleHandler(exchange, conn,"add");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/schedule/all":
                                case "/api/schedule/findOne":
                                case "/api/schedule/findById":
                                    if ("GET".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        ScheduleOperationHandlers.scheduleHandler(exchange, conn,"select");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/schedule/update":
                                    if ("PUT".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        ScheduleOperationHandlers.scheduleHandler(exchange, conn,"update");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/response/add":
                                    if ("POST".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        ResponseOperationHandlers.responseHandler(exchange, conn,"add");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/response/all":
                                case "/api/response/findOne":
                                case "/api/response/findById":
                                    if ("GET".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        ResponseOperationHandlers.responseHandler(exchange, conn,"select");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/response/update":
                                    if ("PUT".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        ResponseOperationHandlers.responseHandler(exchange, conn,"update");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/options/add":
                                    if ("POST".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        OptionsOperationHandlers.optionsHandler(exchange, conn,"add");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/options/all":
                                case "/api/options/findOne":
                                case "/api/options/findById":
                                    if ("GET".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        OptionsOperationHandlers.optionsHandler(exchange, conn,"select");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/options/update":
                                    if ("PUT".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        OptionsOperationHandlers.optionsHandler(exchange, conn,"update");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/teacher/add":
                                    if ("POST".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        TeacherOperationHandlers.teacherHandler(exchange, conn,"add");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/teacher/all":
                                case "/api/teacher/findOne":
                                case "/api/teacher/findById":
                                    if ("GET".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        TeacherOperationHandlers.teacherHandler(exchange, conn,"select");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/teacher/update":
                                    if ("PUT".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        TeacherOperationHandlers.teacherHandler(exchange, conn,"update");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/subject/add":
                                    if ("POST".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        SubjectOperationHandlers.subjectHandler(exchange, conn,"add");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/subject/all":
                                case "/api/subject/findOne":
                                case "/api/subject/findById":
                                    if ("GET".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        SubjectOperationHandlers.subjectHandler(exchange, conn,"select");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/subject/update":
                                    if ("PUT".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        SubjectOperationHandlers.subjectHandler(exchange, conn,"update");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/exam/add":
                                    if ("POST".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        ExamOperationHandlers.examHandler(exchange, conn,"add");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/exam/all":
                                case "/api/exam/findOne":
                                case "/api/exam/findById":
                                    if ("GET".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        ExamOperationHandlers.examHandler(exchange, conn,"select");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/exam/update":
                                    if ("PUT".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        ExamOperationHandlers.examHandler(exchange, conn,"update");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/questions/add":
                                    if ("POST".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        QuestionsOperationHandlers.questionsHandler(exchange, conn,"add");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/questions/all":
                                case "/api/questions/findOne":
                                case "/api/questions/findById":
                                    if ("GET".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        QuestionsOperationHandlers.questionsHandler(exchange, conn,"select");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/questions/update":
                                    if ("PUT".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        QuestionsOperationHandlers.questionsHandler(exchange, conn,"update");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/report/teacherExam":
                                    if ("PUT".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        ExamOperationHandlers.examHandler(exchange, conn,"teacherExam");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/report/studentMerit":
                                    if ("GET".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        ReportOperationHandlers.Handler(exchange, conn,"studentMerit");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                                case "/api/report/studentReport":
                                    if ("GET".equalsIgnoreCase(exchange.getRequestMethod().toString())) {
                                        ReportOperationHandlers.Handler(exchange, conn,"studentReport");
                                    } else {
                                        Utils.respondWithError(exchange, "Method Not Allowed", 405);
                                    }
                                    break;
                               default:
                                    Utils.respondWithError(exchange, "Not Found", 404);
                                    break;
                            }
                        }).build();

                server.start();
                System.out.println("Server started on localhost:"+port);
            }else{
                System.out.println(checker.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

