package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.entities.IndependentValidityPeriod;
import it.polimi.db2.telcoservice_sc42.entities.ServicePackage;
import it.polimi.db2.telcoservice_sc42.entities.ServiceType;
import it.polimi.db2.telcoservice_sc42.exception.BadParametersException;
import it.polimi.db2.telcoservice_sc42.exception.BadlyFormattedOptionalProductException;
import it.polimi.db2.telcoservice_sc42.exception.InvalidChoiceServiceException;
import it.polimi.db2.telcoservice_sc42.services.OptionalProductService;
import it.polimi.db2.telcoservice_sc42.services.PackageService;
import it.polimi.db2.telcoservice_sc42.services.ServiceService;
import it.polimi.db2.telcoservice_sc42.utils.EmployeeSessionRegistry;
import it.polimi.db2.telcoservice_sc42.utils.ParameterRegistry;
import it.polimi.db2.telcoservice_sc42.utils.SafeParser;
import it.polimi.db2.telcoservice_sc42.utils.SessionAttributeRegistry;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet(name = "employeeCreationServlet", value = "/new")
public class EmployeeCreationServlet extends HttpServlet {
    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/OptionalProductService")
    OptionalProductService optionalProductService;

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/ServiceService")
    ServiceService serviceService;

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/PackageService")
    PackageService packageService;


    public EmployeeCreationServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        if ( request.getParameter("createOptionalProduct") != null ) {
            createOptionalProduct(request, response);
        } else if ( request.getParameter("addValidities") != null ) {
            addValidity(request, response);
        } else if ( request.getParameter("createServicePackage") != null ){
            createServicePackage(request, response);
        } else {
            createService(request, response);
        }
    }

    private void createOptionalProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        Date date = getOptionalDate(request, "optional_expiration_date", "optional_expiration_date_input");
        BigDecimal fee = new BigDecimal(request.getParameter("monthly_fee"));

        try {
            optionalProductService.createOptionalProduct(name, fee, date);
        } catch ( BadlyFormattedOptionalProductException exception ) {
            String error = exception.getClass().getSimpleName().replace("Exception", "");
            redirectFailure(request, response, error);
            return;
        }

        redirectSuccess(request, response);
    }


    private void createService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServiceType type = ServiceType.fromString(request.getParameter("types"));
        Date date = getOptionalDate(request, "service_expiration_date", "service_expiration_date_input");
        BigDecimal gbFee = SafeParser.safeParseBigDecimal(request.getParameter("gb_fee"));
        BigDecimal smsFee = SafeParser.safeParseBigDecimal(request.getParameter("sms_fee"));
        BigDecimal callFee = SafeParser.safeParseBigDecimal(request.getParameter("call_fee"));
        Integer gbs = SafeParser.safeParseInteger(request.getParameter("gbs"));
        Integer sms = SafeParser.safeParseInteger(request.getParameter("sms"));
        Integer minutes = SafeParser.safeParseInteger(request.getParameter("minutes"));

        if ( type == null ) {
            redirectFailure(request, response, "Invalid service type");
            return;
        }

        try {
            serviceService.createService(type, date, gbFee, gbs, smsFee, sms, callFee, minutes);
        } catch (BadParametersException exception) {
            redirectFailure(request, response, "Badly formatted parameters");
            return;
        }

        redirectSuccess(request, response);
    }

    void addValidity(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("adding validity period");

        saveSelected(request);

        // get the added validity
        int period = Integer.parseInt(request.getParameter(ParameterRegistry.validityPeriod));
        float fee = Float.parseFloat(request.getParameter(ParameterRegistry.validityFee));
        Date expirationDate = getOptionalDate(request, "validity_expiration_date", "validity_expiration_date_input");

        IndependentValidityPeriod validityPeriod = new IndependentValidityPeriod(period, fee, expirationDate);

        Object attribute = request.getSession().getAttribute(EmployeeSessionRegistry.validities);
        List<IndependentValidityPeriod> periods;

        // add the added validity to the list of validities or create a new list
        if ( attribute == null ) {
            periods = new ArrayList<>();
        } else {
            //noinspection unchecked
            periods = (List<IndependentValidityPeriod>) attribute;
        }

        if ( !isValidValidity(validityPeriod, periods) ) {
            redirectFailure(request, response, "Error in validity");
            return;
        }

        periods.add(validityPeriod);
        request.getSession().setAttribute(EmployeeSessionRegistry.validities, periods);
        System.out.println(periods);

        redirectSuccess(request, response);
    }

    private void saveSelected(HttpServletRequest request) {
        // probably doesn't work because the validity form differs from the package creation form

        // save the selected name and expiration date
        String name = request.getParameter("package_name_input");
        Date expirationDate = getOptionalDate(request, "package_expiration_date", "package_expiration_date_input");
        request.getSession().setAttribute(EmployeeSessionRegistry.selectedServiceName, name);

        // save the selected optionals
        List<Integer> optionals = getSelectedOptionalProducts(request);
        request.getSession().setAttribute(EmployeeSessionRegistry.selectedOptionals, optionals);

        // save the selected services
        List<Integer> services = getSelectedServices(request);
        request.getSession().setAttribute(EmployeeSessionRegistry.selectedServices, optionals);
    }

    private boolean isValidValidity(IndependentValidityPeriod validity, List<IndependentValidityPeriod> validities) {
        return !validities.contains(validity);
    }

    /**
     * Return the ids of the optional products in the request's form.
     *
     * @param request the http request
     * @return a list of Integer ids of the optional products
     */
    private List<Integer> getSelectedOptionalProducts(HttpServletRequest request) {
        // TODO: check for empty selected
        String[] params = request.getParameterValues(ParameterRegistry.optionalsCheckbox);

        if ( params == null ) {
            System.out.println("null params in getSelectedOptionalProducts");
            return new ArrayList<>();
        }

        List<String> selected = Arrays.asList(params);
        return selected.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    /**
     * Return the ids of the services in the request's form.
     *
     * @param request the http request
     * @return a list of Integer ids of the services
     */
    private List<Integer> getSelectedServices(HttpServletRequest request) {
        String[] s = request.getParameterValues("services");
        if ( s == null ) return new ArrayList<>();

        return Arrays.stream(s).map(Integer::parseInt).collect(Collectors.toList());
    }

    private List<IndependentValidityPeriod> getSelectedValidityPeriods(HttpServletRequest request) {
        String[] selected = request.getParameterValues("periods");
        IndependentValidityPeriod p;
        List<IndependentValidityPeriod> periods = new ArrayList<>();

        if ( selected == null ) { return periods; }

        for (String s: selected) {
            p = getIndependentValidityPeriod(s);
            if ( p != null ) { periods.add(p); }
        }

        return periods;
    }

    /**
     * Return the date inserted in a form with a radio button.
     * @param request the http request.
     * @param checkBoxParameter the name given to all radio buttons (i.e. the "name" in <input type="radio" id="some_id" name="name" value="some_value" checked>)
     * @param inputParameter the name of the input tag.
     * @return A Date if the radio button with the date is selected, null otherwise
     */
    private Date getOptionalDate(HttpServletRequest request, String checkBoxParameter, String inputParameter) {
        Date date = null;

        String[] s = request.getParameterValues(checkBoxParameter);
        if ( s == null ) return null;

        List<String> dateValues = Arrays.asList(s);

        if ( dateValues.contains("yes") ) {
            date = Date.valueOf(request.getParameter(inputParameter)) ;
        }

        return date;
    }

    private IndependentValidityPeriod getIndependentValidityPeriod(String str) {
        if ( str == null ) return null;

        String[] parts = str.split(IndependentValidityPeriod.idSeparator);
        int period = SafeParser.safeParseInteger(parts[0]);
        float fee = SafeParser.safeParseFloat(parts[1]);

        System.out.println(parts[2]);
        Date expirationDate = null;
        if (!Objects.equals(parts[2], "null")) {
             expirationDate = Date.valueOf(parts[2]);
        }


        return new IndependentValidityPeriod(period, fee, expirationDate);
    }

    private void createServicePackage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // get package specific info
        String name = request.getParameter("package_name_input");
        Date expirationDate = getOptionalDate(request, "package_expiration_date", "package_expiration_date_input");

        System.out.println("package name: " + name);
        System.out.println("expiration date: " + expirationDate);

        if ( name == null ) {
            redirectFailure(request, response, "Null values");
            return;
        }

        // get optionals ids
        List<Integer> optionals = getSelectedOptionalProducts(request);
        System.out.println("optionals: " + optionals);

        // get services
        List<Integer> services = getSelectedServices(request);
        System.out.println("services: " + services);

        // get periods
        List<IndependentValidityPeriod> periods = getSelectedValidityPeriods(request);

        if ( periods.size() == 0 ) {
            redirectFailure(request, response, "No selected validity period, please create and select one");
            return;
        }

        ServicePackage sp;
        try {
            sp = packageService.createServicePackage(name, expirationDate, services, optionals, periods);
        } catch (InvalidChoiceServiceException e) {
            System.out.println(e.getMessage());
            redirectFailure(request, response, e.getMessage());
            return;
        } catch (BadParametersException e) {
            redirectFailure(request, response, "Bad name. Name too long or too short. ");
            return;
        }

        if ( sp == null ) {
            redirectFailure(request, response, "Error while creating the package. ");
        } else {
            redirectSuccess(request, response);
        }
    }

    private void redirectSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getServletContext().getContextPath() + "/load_optionals");
    }

    private void redirectFailure(HttpServletRequest request, HttpServletResponse response, String error) throws IOException {
        request.getSession().setAttribute(SessionAttributeRegistry.error, error);
        response.sendRedirect(request.getServletContext().getContextPath() + "/employee/home.jsp");
    }
}
