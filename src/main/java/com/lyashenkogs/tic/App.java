package com.lyashenkogs.tic;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App {

    public static void main(String[] args) throws LifecycleException, ClassNotFoundException, SQLException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

 /*  TODO setup blocking connector
     Connector connector = new Connector();
        connector.setProtocolHandlerClassName("org.apache.coyote.http11.Http11Protocol");
        tomcat.setConnector(connector);*/

        Context ctx = tomcat.addContext("fsdf/", new File(".").getAbsolutePath());

        Tomcat.addServlet(ctx, "hello", new RegisterUser());
        Wrapper defaultServlet = ctx.createWrapper();
        defaultServlet.setName("default");
        defaultServlet.setServletClass("org.apache.catalina.servlets.DefaultServlet");
        defaultServlet.addInitParameter("debug", "0");
        defaultServlet.addInitParameter("listings", "false");
        defaultServlet.setLoadOnStartup(1);
        ctx.addChild(defaultServlet);
        ctx.addServletMapping("/", "default");
        ctx.addServletMappingDecoded("/hello", "hello");

        tomcat.start();
        tomcat.getServer().await();
    }


    static class RegisterUser extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            Writer w = resp.getWriter();
            w.write("Hello, World!");
            w.flush();
        }
    }
}

