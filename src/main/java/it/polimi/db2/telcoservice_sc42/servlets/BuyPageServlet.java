package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.entities.ServicePackage;
import it.polimi.db2.telcoservice_sc42.services.PackageService;
import it.polimi.db2.telcoservice_sc42.utils.BuySessionRegistry;
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

    /**
     * The method prepares the refresh of the current page
     * @param request the HttpServletRequest of the page
     * @param response the HttpServletResponse of the page
     * @throws IOException if something goes wrong
     */
    private void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServicePackage servicePackage = setServicePackage(request);
        prepareRefresh(request, servicePackage);
        response.sendRedirect(getServletContext().getContextPath() + "/" + "HTML/buyService.jsp");
    }

    /**
     * The method prepares the redirection to the ConfirmationPage
     * @param request the HttpServletRequest of the page
     * @param response the HttpServletResponse of the page
     * @throws IOException if something goes wrong
     */
    private void buy(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepareConfirmationPage(request);
        // redirect to the confirmation page servlet that loads the details needed
        response.sendRedirect(getServletContext().getContextPath() + "/confirmation");
        // response.sendRedirect(getServletContext().getContextPath() + "/" + "HTML/confirmation.jsp");
    }

    /**
     * The method sets the attributes services, validities and optionals into the webSession so to be used when refreshing the page
     * @param request the HttpServletRequest of the page
     * @param servicePackage the servicePackage that was chosen in advance and already retrieved
     */
    private void prepareRefresh(HttpServletRequest request, ServicePackage servicePackage) {
        request.getSession().setAttribute(BuySessionRegistry.services, servicePackage.getServices());
        request.getSession().setAttribute("validities", servicePackage.getValidities());
        request.getSession().setAttribute("optionals", servicePackage.getProducts());
    }

    /**
     * The method sets the attributes "chosen_validity", "chosen_optionals" and "chosen_subscription" into the webSession, so to use them
     * after the redirection towards the ConfirmationPage
     * @param request the HttpServletRequest of the page
     */
    private void prepareConfirmationPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(BuySessionRegistry.chosenValidity, request.getParameter("available_validity"));
        session.setAttribute(BuySessionRegistry.chosenOptionals, request.getParameterValues("available_optional"));
        session.setAttribute(BuySessionRegistry.chosenSubscription, request.getParameter("starting_date_of_subscription"));
    }

    /**
     * The method retrieves the chosen servicePackage from the current http request
     * @param request the HttpServletRequest of the page
     * @return the chosen ServicePackage
     */
    private ServicePackage setServicePackage(HttpServletRequest request){
        int selectedPackage = SafeParser.safeParseInteger(request.getParameter("selected"));
        request.getSession().setAttribute(BuySessionRegistry.selectedPackage, selectedPackage);

        return packageService.findServicePackageById(selectedPackage);
    }
}
