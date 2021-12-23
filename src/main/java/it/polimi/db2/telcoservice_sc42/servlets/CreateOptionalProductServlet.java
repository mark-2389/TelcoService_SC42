package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.exception.BadlyFormattedOptionalProductException;
import it.polimi.db2.telcoservice_sc42.services.OptionalProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "createOptionalProductServlet", value = "/new_optional_product")
public class CreateOptionalProductServlet extends HttpServlet {
    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/OptionalProductService")
    OptionalProductService optionalProductService;

    public CreateOptionalProductServlet() {
        super();
    }

    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
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

    private void redirectSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getServletContext().getContextPath() + "/employee/home.jsp");
    }

    private void redirectFailure(HttpServletRequest request, HttpServletResponse response, String error) throws IOException {
        request.getSession().setAttribute("error", error);
        response.sendRedirect(request.getServletContext().getContextPath() + "/employee/home.jsp");
    }
}
