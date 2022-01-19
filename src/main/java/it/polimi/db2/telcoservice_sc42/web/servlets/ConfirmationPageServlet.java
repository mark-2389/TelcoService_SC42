package it.polimi.db2.telcoservice_sc42.web.servlets;

import it.polimi.db2.telcoservice_sc42.ejb.entities.*;
import it.polimi.db2.telcoservice_sc42.entities.*;
import it.polimi.db2.telcoservice_sc42.ejb.services.OptionalProductService;
import it.polimi.db2.telcoservice_sc42.ejb.services.OrderService;
import it.polimi.db2.telcoservice_sc42.ejb.services.PackageService;
import it.polimi.db2.telcoservice_sc42.ejb.services.ValidityService;
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
import java.util.stream.Collectors;

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

        //retrieve the order correctly
        Order order = orderService.findOrderById(oid);
        if ( order == null ) return;

        request.getSession().setAttribute(BuySessionRegistry.orderId, oid);

        prepareServicePackageDetails(request, order.getPackageId());
        prepareValidityDetails(request, order.getValidityId());
        prepareSubscriptionDateDetails(request, order.getSubscriptionDate().toString());
        prepareOptionalsDetails(request, order.getOptionalIds());
        prepareServicesDetails(request, order.getPackage().getServices());
    }

    private void prepareDetails(HttpServletRequest request) {
        prepareServicePackageDetails(request, null);
        prepareValidityDetails(request, null);
        prepareOptionalsDetails(request, null);
        // services are already passed by the BuyPageServlet
    }

    private void prepareSubscriptionDateDetails(HttpServletRequest request, String date) {
        request.getSession().setAttribute(BuySessionRegistry.chosenSubscription, date);
    }

    private void prepareServicesDetails(HttpServletRequest request, List<Service> services) {
        // save the name of the selected package
        request.getSession().setAttribute(BuySessionRegistry.services, services);
    }

    private void prepareServicePackageDetails(HttpServletRequest request, Integer packageId) {
        ServicePackage servicePackage;

        if ( packageId == null ) {
            Object attribute = request.getSession().getAttribute(BuySessionRegistry.selectedPackage);

            if (!(attribute instanceof ServicePackage)) return;

            servicePackage = (ServicePackage) attribute;
        } else {
            // get the servicePackage
            servicePackage = packageService.findServicePackageById(packageId);

            request.getSession().setAttribute(BuySessionRegistry.selectedPackage, packageId);
        }

        // save the name of the selected package
        request.getSession().setAttribute(BuySessionRegistry.selectedPackageName, servicePackage.clientString());
    }

    private void prepareValidityDetails(HttpServletRequest request, Integer validityId) {
        Validity validity;

        if ( validityId == null ) {
            Object attribute = request.getSession().getAttribute(BuySessionRegistry.chosenValidity);

            if (!(attribute instanceof Validity)) return;

            validity = (Validity) attribute;
        } else {
            // get the validity
            validity = validityService.findValidityById(validityId);

            request.getSession().setAttribute(BuySessionRegistry.chosenValidity, validity);
        }

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
        List<OptionalProduct> optionals;

        if ( optionalIds == null ) {

            List<Object> listParameter = (List<Object>) (request.getSession().getAttribute(BuySessionRegistry.chosenOptionals));

            if ( listParameter == null ) return;

            optionals = listParameter.stream().map(i -> (OptionalProduct)i).collect(Collectors.toList());

        } else {
            optionals = new ArrayList<>();

            for( Integer id : optionalIds ){
                optionals.add(optionalService.findOptionalProductById(id));
            }

            request.getSession().setAttribute(BuySessionRegistry.chosenOptionals, optionals);
        }

        BigDecimal totalFee = new BigDecimal(0);
        List<String> optionalsString = new ArrayList<>();

        for ( OptionalProduct optional : optionals ){
            totalFee = totalFee.add(optional.getMonthlyFee());
            optionalsString.add(optional.clientString());
        }

        request.getSession().setAttribute(BuySessionRegistry.totalOptionalsFee, totalFee);
        request.getSession().setAttribute(BuySessionRegistry.chosenOptionalsDescriptions, optionalsString);
    }
}
