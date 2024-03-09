package org.example.routes;

import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.websockets.jsr.UndertowSession;
import org.example.handlers.authedication.Validate;
import org.example.libs.Utils;

import static org.example.handlers.authedication.Validate.authenticationMiddleware;

public class Main {

    public static HttpHandler Routes(){
             return   Handlers.path()
                     .addPrefixPath("/classes", Handlers.routing().addAll(Routes.ClassRoutes()))
                     .addPrefixPath("/students",Validate.authenticationMiddleware(Handlers.routing().addAll(Routes.StudentRoutes()),"admin","teacher","super_admin","student"))
                     .addPrefixPath("/exams", Validate.authenticationMiddleware(Handlers.routing().addAll(Routes.ExamRoutes()),"teacher","admin"))
                     .addPrefixPath("/questions",Handlers.routing().addAll(Routes.QuestionRoutes()))
                     .addPrefixPath("/responses", Handlers.routing().addAll(Routes.ResponseRoutes()))
                     .addPrefixPath("/options",Handlers.routing().addAll(Routes.OptionRoutes()))
                     .addPrefixPath("/teachers",Validate.authenticationMiddleware( Handlers.routing().addAll(Routes.TeacherRoutes()),"admin","teacher","super_admin"))
                     .addPrefixPath("/subjects",Handlers.routing().addAll(Routes.SubjectRoutes()))
                     .addPrefixPath("/schedules",Handlers.routing().addAll(Routes.ScheduleRoutes()))
                     .addPrefixPath("/reports", Handlers.routing().addAll(Routes.ReportRoutes()))
                     .addPrefixPath("/authentication", Handlers.routing().addAll(Routes.authedicationRoutes()));
    }


}
