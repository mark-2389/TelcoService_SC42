package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.entities.ServiceType;
import it.polimi.db2.telcoservice_sc42.exception.BadParametersException;
import it.polimi.db2.telcoservice_sc42.exception.BadlyFormattedOptionalProductException;
import it.polimi.db2.telcoservice_sc42.services.OptionalProductService;
import it.polimi.db2.telcoservice_sc42.services.ServiceService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "employeeCreationServlet", value = "/new")
public class EmployeeCreationServlet extends HttpServlet {
    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/OptionalProductService")
    OptionalProductService optionalProductService;

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/ServiceService")
    ServiceService serviceService;

    public EmployeeCreationServlet() {
        super();
    }

    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        if ( request.getParameter("createOptionalProduct") != null ) {
            createOptionalProduct(request, response);
        } else {
            createService(request, response);
        }
    }

    private void createOptionalProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        Date date = Date.valueOf(request.getParameter("expiration_date")) ;
        Float fee = Float.parseFloat(request.getParameter("monthly_fee"));

        try {
            optionalProductService.createOptionalProduct(name, fee, date);
        } catch ( BadlyFormattedOptionalProductException exception ) {
            String error = exception.getClass().getSimpleName().replace("Exception", "");
            redirectFailure(request, response, error);
            return;
        }

        redirectSuccess(request, response);
    }

    private Integer safeParseInteger(String number) {
        int value;
        try {
            value = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return null;
        }

        return value;
    }

    private Float safeParseFloat(String number) {
        float value;
        try {
            value = Float.parseFloat(number);
        } catch (NumberFormatException e) {
            return null;
        }

        return value;
    }

    private void createService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServiceType type = ServiceType.fromString(request.getParameter("types"));
        Date date = Date.valueOf(request.getParameter("expiration_date")) ;
        Float gbFee = safeParseFloat(request.getParameter("gb_fee"));
        Float smsFee = safeParseFloat(request.getParameter("sms_fee"));
        Float callFee = safeParseFloat(request.getParameter("call_fee"));
        Integer gbs = safeParseInteger(request.getParameter("gbs"));
        Integer sms = safeParseInteger(request.getParameter("sms"));
        Integer minutes = safeParseInteger(request.getParameter("minutes"));

        if ( type == null ) {
            redirectFailure(request, response, "Invalid service type");
            return;
        }

        try {
            serviceService.createService(type, date, gbFee, gbs, smsFee, sms, callFee, minutes);
        } catch (BadParametersException exception) {
            redirectFailure(request, response, "Badly formatted parameters");
            return;
        }


        redirectSuccess(request, response);
    }

    void addValidity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // save the selected optionals
        List<String> selected = Arrays.asList(request.getParameterValues("selected"));
        request.getSession().setAttribute("selected_optionals", selected);

        

        redirectSuccess(request, response);
    }

    private void redirectSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getServletContext().getContextPath() + "/load_optionals");
    }

    private void redirectFailure(HttpServletRequest request, HttpServletResponse response, String error) throws IOException {
        request.getSession().setAttribute("error", error);
        response.sendRedirect(request.getServletContext().getContextPath() + "/employee/home.jsp");
    }
}
