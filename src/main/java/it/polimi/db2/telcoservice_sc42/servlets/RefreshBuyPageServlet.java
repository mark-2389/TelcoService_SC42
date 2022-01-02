package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.entities.ServicePackage;
import it.polimi.db2.telcoservice_sc42.services.PackageService;
import it.polimi.db2.telcoservice_sc42.utils.SafeParser;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RefreshBuyPageServlet", value = "/Refresh")
public class RefreshBuyPageServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/PackageService")
    PackageService packageService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("selectedPackage", request.getParameter("selected"));

        preparePage(request);

        response.sendRedirect(getServletContext().getContextPath() + "/" + "HTML/buyService.jsp");
    }

    private void preparePage(HttpServletRequest request) {
        int selectedPackage = SafeParser.safeParseInteger(request.getParameter("selected"));
        ServicePackage servicePackage = packageService.findServicePackageById(selectedPackage);

        request.getSession().setAttribute("services", servicePackage.getServices());
        request.getSession().setAttribute("validities", servicePackage.getValidities());
        request.getSession().setAttribute("optionals", servicePackage.getProducts());
    }
}
