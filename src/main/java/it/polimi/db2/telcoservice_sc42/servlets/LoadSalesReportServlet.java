package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.services.SalesReportService;
import it.polimi.db2.telcoservice_sc42.utils.EmployeeSessionRegistry;
import jakarta.ejb.EJB;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "LoadSalesReportServlet", value = "/load_reports")
public class LoadSalesReportServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/SalesReportService")
    SalesReportService salesReportService;

    public LoadSalesReportServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        loadAverageOptionalProduct(request);
        loadBestOptionalProduct(request);
        loadPurchasesPerPackage(request);
        loadPurchasesPerPackageValidity(request);
        loadValuesPerPackageOptionalProduct(request);
        loadValuesPerPackageWithoutOp(request);
        loadInsolventUsers(request);
        loadSuspendedOrders(request);
        loadAlerts(request);

        redirectSuccess(request, response);
    }

    private void loadAverageOptionalProduct(HttpServletRequest request) {
        // get all the averages per optionalProduct per package
        List<Map<String, String>> averages = salesReportService.getAllAveragesOptionalProductsPerPackage();

        // save the averages in the session, if there are some
        if ( averages.isEmpty() ) return;
        request.getSession().setAttribute(EmployeeSessionRegistry.allAverageOptionalProduct, averages);
    }

    private void loadBestOptionalProduct(HttpServletRequest request) {
        // retrieve the best optionalProduct
        Map<String, String> best = salesReportService.findBestOptionalProduct();

        // save the best product in the session,
        request.getSession().setAttribute(EmployeeSessionRegistry.bestOptionalProduct, best);
    }

    private void loadPurchasesPerPackage(HttpServletRequest request) {
        // get all purchases per package
        List<Map<String, String>> purchases = salesReportService.getAllPurchasesPerPackage();

        // save purchases in the session, if there are some
        if ( purchases.isEmpty() ) return;
        request.getSession().setAttribute(EmployeeSessionRegistry.allPurchasesPerPackage, purchases);
    }

    private void loadPurchasesPerPackageValidity(HttpServletRequest request) {
        // retrieve purchases per package and validity
        List<Map<String, String>> purchasesValidity = salesReportService.getAllPurchasesPerPackageValidity();

        // save purchasesValidity in the session, if there are some
        if ( purchasesValidity.isEmpty() ) return;
        request.getSession().setAttribute(EmployeeSessionRegistry.allPurchasesPerPackageValidity, purchasesValidity);
    }

    private void loadValuesPerPackageOptionalProduct(HttpServletRequest request) {
        // retrieve values of sales per package with OptionalProducts
        List<Map<String, String>> valuesOptional = salesReportService.getAllValuePerPackageWithOptionalProduct();

        // save values in the session, if there are some
        if ( valuesOptional.isEmpty() ) return;
        request.getSession().setAttribute(EmployeeSessionRegistry.allValuesPerPackageOptionalProduct, valuesOptional);
    }

    private void loadValuesPerPackageWithoutOp(HttpServletRequest request) {
        // retrieve values of sales per package with OptionalProducts
        List<Map<String, String>> values = salesReportService.getAllValuePerPackageWithoutOp();

        // save values in the session, if there are some
        if ( values.isEmpty() ) return;
        request.getSession().setAttribute(EmployeeSessionRegistry.allValuesPerPackageWithoutOp, values);
    }

    private void loadInsolventUsers(HttpServletRequest request) {
        // retrieve insolvent Users
        List<Map<String, String>> users = salesReportService.insolventUsers();

        // save users in the session, if there are some
        if ( users.isEmpty() ) return;
        request.getSession().setAttribute(EmployeeSessionRegistry.insolventUsers, users);
    }

    private void loadSuspendedOrders(HttpServletRequest request) {
        // retrieve suspended Orders
        List<Map<String, String>> orders = salesReportService.suspendedOrders();

        // save orders in the session, if there are some
        if ( orders.isEmpty() ) return;
        request.getSession().setAttribute(EmployeeSessionRegistry.suspendedOrders, orders);
    }

    private void loadAlerts(HttpServletRequest request) {
        // retrieve active alerts
        List<Map<String, String>> alerts = salesReportService.getAlerts();

        // save alerts in the session, if there are some
        if ( alerts.isEmpty() ) return;
        request.getSession().setAttribute(EmployeeSessionRegistry.activeAlerts, alerts);
    }


    private void redirectSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getServletContext().getContextPath() + "/employee/salesReport.jsp");
    }
}
