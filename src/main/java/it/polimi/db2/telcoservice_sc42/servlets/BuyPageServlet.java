package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.entities.OptionalProduct;
import it.polimi.db2.telcoservice_sc42.entities.ServicePackage;
import it.polimi.db2.telcoservice_sc42.entities.Validity;
import it.polimi.db2.telcoservice_sc42.services.OptionalProductService;
import it.polimi.db2.telcoservice_sc42.services.PackageService;
import it.polimi.db2.telcoservice_sc42.services.ValidityService;
import it.polimi.db2.telcoservice_sc42.utils.BuySessionRegistry;
import it.polimi.db2.telcoservice_sc42.utils.SafeParser;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "BuyPageServlet", value = "/BuyPage")
public class BuyPageServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/PackageService")
    PackageService packageService;
    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/ValidityService")
    ValidityService validityService;
    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/OptionalProductService")
    OptionalProductService optionalService;

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
        ServicePackage servicePackage = handleServicePackage(request, "selected", true);
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

        session.setAttribute(BuySessionRegistry.selectedPackage, handleServicePackage(request, BuySessionRegistry.selectedPackage, false));
        session.setAttribute(BuySessionRegistry.chosenValidity, retrieveChosenValidity(request));
        session.setAttribute(BuySessionRegistry.chosenOptionals, retrieveOptionalProduct(request));
        session.setAttribute(BuySessionRegistry.chosenSubscription, request.getParameter("starting_date_of_subscription"));
    }

    /**
     * The method retrieves the chosen servicePackage from the current http request
     * @param request the HttpServletRequest of the page
     * @return the chosen ServicePackage
     */
    private ServicePackage handleServicePackage(HttpServletRequest request, String parameter, boolean saveById){
        Integer selectedPackage;

        if ( saveById ) {
            selectedPackage = SafeParser.safeParseInteger(request.getParameter(parameter));
            request.getSession().setAttribute(BuySessionRegistry.selectedPackage, selectedPackage);
        }
        else
            selectedPackage = (Integer) request.getSession().getAttribute(parameter);

        return packageService.findServicePackageById(selectedPackage);
    }

    private Validity retrieveChosenValidity(HttpServletRequest request){
        String parameter = request.getParameter("available_validity");

        //safeParser
        int validityId = SafeParser.safeParseInteger(parameter);

        return validityService.findValidityById(validityId);
    }


    private List<OptionalProduct> retrieveOptionalProduct(HttpServletRequest request){

        return Arrays.stream(request.getParameterValues("available_optional")).
                        map(SafeParser::safeParseInteger).
                            map(id -> optionalService.findOptionalProductById(id)).
                                collect(Collectors.toList());
    }
}
