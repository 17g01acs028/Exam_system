package org.example.routes;

import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.BlockingHandler;
import org.example.handlers.authedication.Login;
import org.example.handlers.authedication.Refresh;
import org.example.handlers.authedication.Validate;
import org.example.handlers.base.Dispatcher;
import org.example.handlers.classes.*;
import org.example.handlers.exams.CreateExam;
import org.example.handlers.exams.GetExam;
import org.example.handlers.exams.GetExams;
import org.example.handlers.exams.UpdateExam;
import org.example.handlers.options.CreateOption;
import org.example.handlers.options.GetOption;
import org.example.handlers.options.GetOptions;
import org.example.handlers.options.UpdateOption;
import org.example.handlers.questions.CreateQuestion;
import org.example.handlers.questions.GetQuestion;
import org.example.handlers.questions.GetQuestions;
import org.example.handlers.questions.UpdateQuestion;
import org.example.handlers.reports.FullExamReport;
import org.example.handlers.reports.StudentScore;
import org.example.handlers.reports.TeacherExam;
import org.example.handlers.responses.CreateResponse;
import org.example.handlers.responses.GetResponse;
import org.example.handlers.responses.GetResponses;
import org.example.handlers.responses.UpdateResponse;
import org.example.handlers.schedules.CreateSchedule;
import org.example.handlers.schedules.GetSchedule;
import org.example.handlers.schedules.GetSchedules;
import org.example.handlers.schedules.UpdateSchedule;
import org.example.handlers.students.CreateStudent;
import org.example.handlers.students.GetStudent;
import org.example.handlers.students.GetStudents;
import org.example.handlers.students.UpdateStudent;
import org.example.handlers.subjects.CreateSubject;
import org.example.handlers.subjects.GetSubject;
import org.example.handlers.subjects.GetSubjects;
import org.example.handlers.subjects.UpdateSubject;
import org.example.handlers.teachers.CreateTeacher;
import org.example.handlers.teachers.GetTeacher;
import org.example.handlers.teachers.GetTeachers;
import org.example.handlers.teachers.UpdateTeacher;
import org.example.libs.Utils;

public class Routes {
    public static RoutingHandler ClassRoutes() {
        return Handlers.routing()
                .get("", Validate.authenticationMiddleware(new Dispatcher(GetClasses::Handler),"admin","teacher","student"))
                .get("/{id}",Validate.authenticationMiddleware(new Dispatcher(GetClass::Handler),"admin","teacher","student"))
                .put("/{id}",Validate.authenticationMiddleware(new Dispatcher(new BlockingHandler( UpdateClass::Handler)),"admin","teacher","super_admin"))
                .post("",Validate.authenticationMiddleware(new Dispatcher(new BlockingHandler( CreateClass::Handler)),"admin","teacher","super_admin"))
                .setFallbackHandler(exchange -> {
                    Utils.respondWithError(exchange, exchange.getRequestMethod()+ ": Method Not Allowed", 405);
                });
    }

    public static RoutingHandler StudentRoutes() {
        return Handlers.routing()
                .get("", new Dispatcher(GetStudents::Handler))
                .get("/{id}",new Dispatcher(GetStudent::Handler))
                .put("/{id}",new Dispatcher(new BlockingHandler(UpdateStudent::Handler)))
                .post("",new Dispatcher(new BlockingHandler( CreateStudent::Handler)))
                .setFallbackHandler(exchange -> {
                    Utils.respondWithError(exchange, exchange.getRequestMethod()+ ": Method Not Allowed", 405);
                });
    }

    public static RoutingHandler TeacherRoutes() {
        return Handlers.routing()
                .get("", new Dispatcher(GetTeachers::Handler))
                .get("/{id}",new Dispatcher(GetTeacher::Handler))
                .put("/{id}",new Dispatcher(new BlockingHandler(UpdateTeacher::Handler)))
                .post("",new Dispatcher(new BlockingHandler( CreateTeacher::Handler)))
                .setFallbackHandler(exchange -> {
                    Utils.respondWithError(exchange, exchange.getRequestMethod()+ ": Method Not Allowed", 405);
                });
    }

    public static RoutingHandler ExamRoutes() {
        return Handlers.routing()
                .get("", new Dispatcher(GetExams::Handler))
                .get("/{id}",new Dispatcher(GetExam::Handler))
                .put("/{id}",new Dispatcher(new BlockingHandler(UpdateExam::Handler)))
                .post("",new Dispatcher(new BlockingHandler( CreateExam::Handler)))
                .setFallbackHandler(exchange -> {
                    Utils.respondWithError(exchange, exchange.getRequestMethod()+ ": Method Not Allowed", 405);
                });
    }

    public static RoutingHandler QuestionRoutes() {
        return Handlers.routing()
                .get("", Validate.authenticationMiddleware(new Dispatcher(GetQuestions::Handler),"admin","teacher","student"))
                .get("/{id}",Validate.authenticationMiddleware(new Dispatcher(GetQuestion::Handler),"admin","teacher","student"))
                .put("/{id}",Validate.authenticationMiddleware(new Dispatcher(new BlockingHandler(UpdateQuestion::Handler)),"admin","teacher"))
                .post("",Validate.authenticationMiddleware(new Dispatcher(new BlockingHandler( CreateQuestion::Handler)),"admin","teacher"))
                .setFallbackHandler(exchange -> {
                    Utils.respondWithError(exchange, exchange.getRequestMethod()+ ": Method Not Allowed", 405);
                });
    }

    public static RoutingHandler ScheduleRoutes() {
        return Handlers.routing()
                .get("",Validate.authenticationMiddleware( new Dispatcher(GetSchedules::Handler),"admin","teacher","student"))
                .get("/{id}",Validate.authenticationMiddleware(new Dispatcher(GetSchedule::Handler),"admin","teacher","student"))
                .put("/{id}",Validate.authenticationMiddleware(new Dispatcher(new BlockingHandler(UpdateSchedule::Handler)),"admin","teacher"))
                .post("",Validate.authenticationMiddleware(new Dispatcher(new BlockingHandler( CreateSchedule::Handler)),"admin","teacher"))
                .setFallbackHandler(exchange -> {
                    Utils.respondWithError(exchange, exchange.getRequestMethod()+ ": Method Not Allowed", 405);
                });
    }

    public static RoutingHandler SubjectRoutes() {
        return Handlers.routing()
                .get("", Validate.authenticationMiddleware(new Dispatcher(GetSubjects::Handler),"admin","teacher","student"))
                .get("/{id}",Validate.authenticationMiddleware(new Dispatcher(GetSubject::Handler),"admin","teacher","student"))
                .put("/{id}",Validate.authenticationMiddleware(new Dispatcher(new BlockingHandler(UpdateSubject::Handler)),"admin","teacher"))
                .post("",Validate.authenticationMiddleware(new Dispatcher(new BlockingHandler( CreateSubject::Handler)),"admin","teacher"))
                .setFallbackHandler(exchange -> {
                    Utils.respondWithError(exchange, exchange.getRequestMethod()+ ": Method Not Allowed", 405);
                });
    }
    public static RoutingHandler ResponseRoutes() {
        return Handlers.routing()
                .get("", Validate.authenticationMiddleware(new Dispatcher(GetResponses::Handler),"admin","teacher","student"))
                .get("/{id}",Validate.authenticationMiddleware(new Dispatcher(GetResponse::Handler),"admin","teacher","student"))
                .put("/{id}",Validate.authenticationMiddleware(new Dispatcher(new BlockingHandler(UpdateResponse::Handler)),"admin","teacher","student"))
                .post("",Validate.authenticationMiddleware(new Dispatcher(new BlockingHandler( CreateResponse::Handler)),"admin","teacher","student"))
                .setFallbackHandler(exchange -> {
                    Utils.respondWithError(exchange, exchange.getRequestMethod()+ ": Method Not Allowed", 405);
                });
    }
    public static RoutingHandler authedicationRoutes() {
        return Handlers.routing()
                .put("/login", new Dispatcher(Login::Handler))
                .get("/refresh",new Dispatcher(Refresh::Handler))
                .setFallbackHandler(exchange -> {
                    Utils.respondWithError(exchange, exchange.getRequestMethod()+ ": Method Not Allowed", 405);
                });
    }
    public static RoutingHandler OptionRoutes() {
        return Handlers.routing()
                .get("", Validate.authenticationMiddleware(new Dispatcher(GetOptions::Handler),"admin","teacher","student"))
                .get("/{id}",Validate.authenticationMiddleware(new Dispatcher(GetOption::Handler),"admin","teacher","student"))
                .put("/{id}",Validate.authenticationMiddleware(new Dispatcher(new BlockingHandler(UpdateOption::Handler)),"admin","teacher","student"))
                .post("",Validate.authenticationMiddleware(new Dispatcher(new BlockingHandler( CreateOption::Handler)),"admin","teacher","student"))
                .setFallbackHandler(exchange -> {
                    Utils.respondWithError(exchange, exchange.getRequestMethod()+ ": Method Not Allowed", 405);
                });
    }
    public static RoutingHandler ReportRoutes() {
        return Handlers.routing()
                .get("/teacher-exam/{id}", Validate.authenticationMiddleware(new Dispatcher(TeacherExam::Handler),"admin","teacher"))
                .get("/student-score/{e_id}/{s_id}",Validate.authenticationMiddleware(new Dispatcher(new BlockingHandler(StudentScore::Handler)),"admin","teacher","student"))
                .get("/general-exam-report/{e_id}",Validate.authenticationMiddleware(new Dispatcher(new BlockingHandler(FullExamReport::Handler)),"admin","teacher"))
                .setFallbackHandler(exchange -> {
                    Utils.respondWithError(exchange, exchange.getRequestMethod()+ ": Method Not Allowed", 405);
                });
    }


}
