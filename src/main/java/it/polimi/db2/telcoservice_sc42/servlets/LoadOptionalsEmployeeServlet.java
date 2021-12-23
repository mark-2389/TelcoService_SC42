package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.entities.OptionalProduct;
import it.polimi.db2.telcoservice_sc42.services.OptionalProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoadOptionalsEmployeeServlet", value = "/load_optionals")
public class LoadOptionalsEmployeeServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/OptionalProductService")
    OptionalProductService optionalProductService;

    final String allOptionalsAttribute = "optionals";

    public LoadOptionalsEmployeeServlet() { }

    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // get all the optionals available
        List<OptionalProduct> optionals = optionalProductService.findValidOptionalProducts();

        // save the optionals in the session
        request.getSession().setAttribute(allOptionalsAttribute, optionals);

        redirectSuccess(request, response);
    }

    private void redirectSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getServletContext().getContextPath() + "/employee/home.jsp");
    }
}
