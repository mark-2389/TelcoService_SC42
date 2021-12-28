package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.entities.*;
import it.polimi.db2.telcoservice_sc42.exception.BadParametersException;
import it.polimi.db2.telcoservice_sc42.exception.BadlyFormattedOptionalProductException;
import it.polimi.db2.telcoservice_sc42.services.OptionalProductService;
import it.polimi.db2.telcoservice_sc42.services.PackageService;
import it.polimi.db2.telcoservice_sc42.services.ServiceService;
import it.polimi.db2.telcoservice_sc42.utils.ParameterRegistry;
import it.polimi.db2.telcoservice_sc42.utils.SafeParser;
import it.polimi.db2.telcoservice_sc42.utils.SessionAttributeRegistry;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
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

    public void init() throws ServletException {
        super.init();
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

        // save the selected optionals
        // List<OptionalProduct> optionals = getSelectedOptionalProducts(request);
        // request.getSession().setAttribute(SessionAttributeRegistry.selectedOptionals, optionals);

        // get the added validity
        int period = Integer.parseInt(request.getParameter(ParameterRegistry.validityPeriod));
        float fee = Float.parseFloat(request.getParameter(ParameterRegistry.validityFee));
        IndependentValidityPeriod validityPeriod = new IndependentValidityPeriod(period, fee);

        Object attribute = request.getSession().getAttribute(SessionAttributeRegistry.validities);
        List<IndependentValidityPeriod> periods;

        // add the added validity to the list of validities or create a new list
        if ( attribute == null ) {
            periods = new ArrayList<>();
        } else {
            periods = (List<IndependentValidityPeriod>) attribute;
        }

        if ( !isValidValidity(validityPeriod, periods) ) {
            redirectFailure(request, response, "Error in validity");
            return;
        }

        periods.add(validityPeriod);
        request.getSession().setAttribute(SessionAttributeRegistry.validities, periods);
        System.out.println(periods);

        redirectSuccess(request, response);
    }

    private boolean isValidValidity(IndependentValidityPeriod validity, List<IndependentValidityPeriod> validities) {
        return !validities.contains(validity);
    }

    private List<OptionalProduct> getSelectedOptionalProducts(HttpServletRequest request) {
        // TODO: check for empty selected
        String[] params = request.getParameterValues(ParameterRegistry.optionalsCheckbox);

        if ( params == null ) {
            System.out.println("null params in getSelectedOptionalProducts");
            return new ArrayList<>();
        }

        List<String> selected = Arrays.asList(params);
        List<Integer> ids = selected.stream().map(Integer::parseInt).collect(Collectors.toList());
        List<OptionalProduct> optionals = new ArrayList<>();

        for (int id: ids) {
            optionals.add(optionalProductService.findOptionalProductById(id));
        }

        return optionals;
    }

    private List<Service> getSelectedServices(HttpServletRequest request) {
        List<String> selected = Arrays.asList(request.getParameterValues("services"));
        List<Integer> ids = selected.stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Service> services = new ArrayList<>();

        for (int id: ids) {
            services.add(serviceService.findServiceById(id));
        }

        return services;
    }

    private List<IndependentValidityPeriod> getSelectedValidityPeriods(HttpServletRequest request) {
        String[] selected = request.getParameterValues("periods");
        IndependentValidityPeriod p;
        List<IndependentValidityPeriod> periods = new ArrayList<>();

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
        List<String> dateValues = Arrays.asList(request.getParameterValues(checkBoxParameter));
        if ( dateValues.contains("yes") ) {
            date = Date.valueOf(request.getParameter(inputParameter)) ;
        }

        return date;
    }

    private IndependentValidityPeriod getIndependentValidityPeriod(String str) {
        if ( str == null ) return null;

        System.out.println("independent validity period: " + str);

        String[] parts = str.split("-");
        System.out.println("independent validity period: " + Arrays.toString(parts));
        int period = SafeParser.safeParseInteger(parts[0]);
        float fee = SafeParser.safeParseFloat(parts[1]);

        return new IndependentValidityPeriod(period, fee);
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



        // id NOT NEEDED
        // name DONE
        // expirationDate DONE
        // services
        // periods
        // optionals

        // get optionals
        List<OptionalProduct> optionals = getSelectedOptionalProducts(request);

        System.out.println("optionals: " + optionals);

        // get services
        List<Service> services = getSelectedServices(request);
        System.out.println("services: " + services);

        ServicePackage sp = packageService.createServicePackage(name, expirationDate, services, optionals);

        // get periods
        List<IndependentValidityPeriod> periods = getSelectedValidityPeriods(request);
        List<Validity> validities = periods.stream().map(p -> p.getValidityWith(sp)).collect(Collectors.toList());
        validities.forEach(v -> packageService.addValidity(sp.getId(), v));

        System.out.println("validities: " + validities);

        redirectSuccess(request, response);
    }

    private void redirectSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getServletContext().getContextPath() + "/load_optionals");
    }

    private void redirectFailure(HttpServletRequest request, HttpServletResponse response, String error) throws IOException {
        request.getSession().setAttribute("error", error);
        response.sendRedirect(request.getServletContext().getContextPath() + "/employee/home.jsp");
    }
}
