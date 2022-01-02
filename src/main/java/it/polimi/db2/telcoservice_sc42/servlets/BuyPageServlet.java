package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.entities.ServicePackage;
import it.polimi.db2.telcoservice_sc42.services.PackageService;
import it.polimi.db2.telcoservice_sc42.utils.SafeParser;
import jakarta.ejb.EJB;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "BuyPageServlet", value = "/BuyPage")
public class BuyPageServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/PackageService")
    PackageService packageService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ( request.getParameter("selected") != null )
            this.refresh(request, response);
        else
            this.buy(request, response);
    }

    private void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServicePackage servicePackage = setServicePackage(request);
        prepareRefresh(request, servicePackage);
        response.sendRedirect(getServletContext().getContextPath() + "/" + "HTML/buyService.jsp");
    }

    private void buy(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepareConfirmationPage(request);
        response.sendRedirect(getServletContext().getContextPath() + "/" + "HTML/confirmation.jsp");
    }

    private void prepareRefresh(HttpServletRequest request, ServicePackage servicePackage) {
        request.getSession().setAttribute("services", servicePackage.getServices());
        request.getSession().setAttribute("validities", servicePackage.getValidities());
        request.getSession().setAttribute("optionals", servicePackage.getProducts());
    }

    private void prepareConfirmationPage(HttpServletRequest request) {
        request.getSession().setAttribute("chosen_validity", request.getParameter("available_validity"));
        request.getSession().setAttribute("chosen_optionals", request.getParameterValues("available_optional"));
        request.getSession().setAttribute("chosen_subscription", request.getParameter("starting_date_of_subscription"));
    }

    private ServicePackage setServicePackage(HttpServletRequest request){
        int selectedPackage = SafeParser.safeParseInteger(request.getParameter("selected"));
        request.getSession().setAttribute("selectedPackage", selectedPackage);

        return packageService.findServicePackageById(selectedPackage);
    }
}
