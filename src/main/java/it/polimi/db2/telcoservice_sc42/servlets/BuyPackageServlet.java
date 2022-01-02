package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.entities.ServicePackage;
import it.polimi.db2.telcoservice_sc42.services.PackageService;
import it.polimi.db2.telcoservice_sc42.utils.SafeParser;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "BuyPackageServlet", value = "/Buy")
public class BuyPackageServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/PackageService")
    PackageService packageService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        preparePage(request);

        response.sendRedirect(getServletContext().getContextPath() + "/" + "HTML/confirmation.jsp");
    }

    private void preparePage(HttpServletRequest request) {
        int selectedId = SafeParser.safeParseInteger((String) request.getSession().getAttribute("selectedPackage"));
        ServicePackage servicePackage = packageService.findServicePackageById(selectedId);

        request.getSession().setAttribute("services", servicePackage.getServices());
        request.getSession().setAttribute("chosen_validity", request.getParameter("available_validity"));
        request.getSession().setAttribute("chosen_optionals", request.getParameterValues("available_optional"));
        request.getSession().setAttribute("chosen_subscription", request.getParameter("starting_date_of_subscription"));
    }
}
