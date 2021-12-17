package it.polimi.db2.telcoservice_sc42;

import java.io.*;
import java.sql.DriverManager;

import it.polimi.db2.telcoservice_sc42.entities.Client;
import it.polimi.db2.telcoservice_sc42.services.LoginService;
import jakarta.annotation.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.inject.Inject;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/LoginService")
    private LoginService loginService;

    public HelloServlet() {
        super();
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String DB_URL = "jdbc:mysql://localhost:3306/telcoservice_db";
        final String USER = "telco";
        final String PASS = "jAv_4:Ski:FO";
        String result = "Connection worked";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            result = "Connection failed";
            e.printStackTrace();
        }

        System.out.println(result);

        handleRequest(request, response);
    }

    public void handleRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {

        PrintWriter out = res.getWriter();
        res.setContentType("text/plain");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Client client;

        try {
            client = loginService.checkCredentials(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            client = new Client();
        }


        out.write("username");
        out.write(" = ");
        out.write(client.getUsername());
        out.write("\n");
        out.write("password");
        out.write(" = ");
        out.write(client.getPassword());
        out.write("\n");
        out.close();

    }

    public void destroy() {
    }
}