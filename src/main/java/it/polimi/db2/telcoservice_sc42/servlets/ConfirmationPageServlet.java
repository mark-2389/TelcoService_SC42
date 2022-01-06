package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.entities.*;
import it.polimi.db2.telcoservice_sc42.services.OptionalProductService;
import it.polimi.db2.telcoservice_sc42.services.OrderService;
import it.polimi.db2.telcoservice_sc42.services.PackageService;
import it.polimi.db2.telcoservice_sc42.services.ValidityService;
import it.polimi.db2.telcoservice_sc42.utils.BuySessionRegistry;
import it.polimi.db2.telcoservice_sc42.utils.SafeParser;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "confirmationPageServlet", value = "/confirmation")
public class ConfirmationPageServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/OrderService")
    OrderService orderService;

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/PackageService")
    PackageService packageService;

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/ValidityService")
    ValidityService validityService;

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/OptionalProductService")
    OptionalProductService optionalService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderId = request.getParameter("order");

        // if the parameter "package" in the request isn't null then the client is trying to pay an insolvent order
        if ( orderId != null ) {
            prepareInsolventDetails(request, orderId);
        } else {
            prepareDetails(request);
        }

        response.sendRedirect(getServletContext().getContextPath() + "/" + "HTML/confirmation.jsp");
    }

    private void prepareInsolventDetails(HttpServletRequest request, String orderId) {
        // get the id of the order as an integer
        Integer oid = SafeParser.safeParseInteger(orderId);
        if ( oid == null ) return;
        request.getSession().setAttribute(BuySessionRegistry.orderId, oid);

        Order order = orderService.findOrderById(oid);
        if ( order == null ) return;

        prepareServicePackageDetails(request, order.getPackageId());
        prepareValidityDetails(request, order.getValidityId());
        prepareSubscriptionDateDatails(request, order.getSubscriptionDate().toString());
        prepareOptionalsDetails(request, order.getOptionalIds());
        prepareServicesDetails(request, order.getPackage().getServices());
    }

    private void prepareDetails(HttpServletRequest request) {
        prepareServicePackageDetails(request, null);
        prepareValidityDetails(request, null);
        prepareOptionalsDetails(request, null);
        // services are already passed by the BuyPageServlet
    }

    private void prepareSubscriptionDateDatails(HttpServletRequest request, String date) {
        request.getSession().setAttribute(BuySessionRegistry.chosenSubscription, date);
    }

    private void prepareServicesDetails(HttpServletRequest request, List<Service> services) {
        // save the name of the selected package
        request.getSession().setAttribute(BuySessionRegistry.services, services);
    }

    private void prepareServicePackageDetails(HttpServletRequest request, Integer packageId) {
        int id;

        if ( packageId == null ) {
            Object attribute = request.getSession().getAttribute(BuySessionRegistry.selectedPackage);

            if (!(attribute instanceof Integer)) return;

            // get the id of the service package
            id = (Integer) attribute;
        } else {
            id = packageId;
            request.getSession().setAttribute(BuySessionRegistry.selectedPackage, id);
        }


        // get the servicePackage
        ServicePackage servicePackage = packageService.findServicePackageById(id);

        // save the name of the selected package
        request.getSession().setAttribute(BuySessionRegistry.selectedPackageName, servicePackage.clientString());
    }

    private void prepareValidityDetails(HttpServletRequest request, Integer validityId) {
        int validity_id;

        if ( validityId == null ) {
            Object attribute = request.getSession().getAttribute(BuySessionRegistry.chosenValidity);

            if (!(attribute instanceof String)) return;

            // get the id of the validity period
            validity_id = SafeParser.safeParseInteger((String) attribute);
        } else {
            validity_id = validityId;
            request.getSession().setAttribute(BuySessionRegistry.chosenValidity, "" + validity_id);
        }

        // get the validity
        Validity validity = validityService.findValidityById(validity_id);

        if ( validity == null ) {
            System.out.println("No validity found");
            return;
        }

        // save months
        request.getSession().setAttribute(BuySessionRegistry.chosenValidityMonths, validity.getPeriod());

        // save fee
        request.getSession().setAttribute(BuySessionRegistry.chosenValidityFee, validity.getMonthlyFee());
    }

    private void prepareOptionalsDetails(HttpServletRequest request, List<Integer> optionalIds) {
        String[] optional_Ids;

        if ( optionalIds == null ) {
            optional_Ids = (String[]) request.getSession().getAttribute(BuySessionRegistry.chosenOptionals);
        } else {
            optional_Ids = new String[optionalIds.size()];
            for (int i=0; i<optionalIds.size(); i++) optional_Ids[i] = optionalIds.get(i) + "";
            request.getSession().setAttribute(BuySessionRegistry.chosenOptionals, optional_Ids);
        }

        List<String> optionals = new ArrayList<>();

        Integer id;
        BigDecimal totalFee = new BigDecimal(0);
        OptionalProduct optional;

        for ( String sid: optional_Ids ) {
            id = SafeParser.safeParseInteger(sid);
            if ( id != null ) {
                optional = optionalService.findOptionalProductById(id);
                optionals.add(optional.clientString());
                totalFee = totalFee.add(optional.getMonthlyFee());
            }
        }

        request.getSession().setAttribute(BuySessionRegistry.totalOptionalsFee, totalFee);
        request.getSession().setAttribute(BuySessionRegistry.chosenOptionalsDescriptions, optionals);
    }
}
