package it.polimi.db2.telcoservice_sc42.web.servlets;

import it.polimi.db2.telcoservice_sc42.ejb.entities.OptionalProduct;
import it.polimi.db2.telcoservice_sc42.ejb.entities.Service;
import it.polimi.db2.telcoservice_sc42.ejb.services.OptionalProductService;
import it.polimi.db2.telcoservice_sc42.ejb.services.ServiceService;
import it.polimi.db2.telcoservice_sc42.utils.EmployeeSessionRegistry;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoadOptionalsEmployeeServlet", value = "/load_optionals")
public class LoadOptionalsEmployeeServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.ejb.services/OptionalProductService")
    OptionalProductService optionalProductService;

    @EJB(name = "it.polimi.db2.telcoservice_sc42.ejb.services/ServiceService")
    ServiceService serviceService;

    public LoadOptionalsEmployeeServlet() { }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        loadOptionals(request);
        loadServices(request);

        redirectSuccess(request, response);
    }

    private void loadOptionals(HttpServletRequest request) {
        // get all the optionals available
        List<OptionalProduct> optionals = optionalProductService.findValidOptionalProducts();

        // save the optionals in the session
        request.getSession().setAttribute(EmployeeSessionRegistry.allOptionalsAttribute, optionals);
    }

    private void loadServices(HttpServletRequest request) {
        // get all the optionals available
        List<Service> services = serviceService.findValidServices();

        // save the optionals in the session
        request.getSession().setAttribute(EmployeeSessionRegistry.allServicesAttribute, services);
    }

    private void redirectSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getServletContext().getContextPath() + "/employee/home.jsp");
    }
}
