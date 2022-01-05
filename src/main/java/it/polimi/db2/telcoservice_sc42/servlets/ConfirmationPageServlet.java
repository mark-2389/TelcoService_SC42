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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "confirmationPageServlet", value = "/confirmation")
public class ConfirmationPageServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/PackageService")
    PackageService packageService;

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/ValidityService")
    ValidityService validityService;

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/OptionalProductService")
    OptionalProductService optionalService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prepareDetails(request);
        response.sendRedirect(getServletContext().getContextPath() + "/" + "HTML/confirmation.jsp");
    }

    private void prepareDetails(HttpServletRequest request) {
        prepareServicePackageDetails(request);
        prepareValidityDetails(request);
        prepareOptionalsDetails(request);
    }

    private void prepareServicePackageDetails(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(BuySessionRegistry.selectedPackage);

        if ( !(attribute instanceof Integer) )  return;

        // get the id of the service package
        int id = (Integer) attribute;

        // get the servicePackage
        ServicePackage servicePackage = packageService.findServicePackageById(id);

        // save the name of the selected package
        request.getSession().setAttribute(BuySessionRegistry.selectedPackageName, servicePackage.getName());
    }

    private void prepareValidityDetails(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(BuySessionRegistry.chosenValidity);

        if ( !(attribute instanceof String) ) return;

        // get the id of the validity period
        int validity_id =  SafeParser.safeParseInteger((String) attribute);

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

    private void prepareOptionalsDetails(HttpServletRequest request) {
        String[] optionalIds = (String[]) request.getSession().getAttribute(BuySessionRegistry.chosenOptionals);

        List<String> optionals = new ArrayList<>();

        Integer id;
        BigDecimal totalFee = new BigDecimal(0);
        OptionalProduct optional;

        for ( String sid: optionalIds ) {
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
