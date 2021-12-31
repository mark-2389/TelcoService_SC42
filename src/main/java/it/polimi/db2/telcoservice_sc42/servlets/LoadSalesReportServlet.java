package it.polimi.db2.telcoservice_sc42.servlets;

import it.polimi.db2.telcoservice_sc42.services.SalesReportService;
import it.polimi.db2.telcoservice_sc42.views.*;
import jakarta.ejb.EJB;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoadSalesReportServlet", value = "/load_reports")
public class LoadSalesReportServlet extends HttpServlet {

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/SalesReportService")
    SalesReportService salesReportService;

    final String allAverageOptionalProduct = "averages";
    final String bestOptionalProduct = "best";
    final String allPurchasesPerPackage = "purchases";
    final String allPurchasesPerPackageValidity = "purchasesValidity";
    final String allValuesPerPackageOptionalProduct = "valuesPackageOptional";
    final String allValuesPerPackageWithoutOp = "values";
    final String insolventUsers = "insolvents";
    final String suspendedOrders = "orders";
    final String activeAlerts = "alerts";

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
        List<String> averages = salesReportService.getAllAveragesOptionalProductsPerPackage();

        // save the averages in the session
        request.getSession().setAttribute(allAverageOptionalProduct, averages);
    }

    private void loadBestOptionalProduct(HttpServletRequest request) {
        // retrieve the best optionalProduct
        String best = salesReportService.findBestOptionalProduct();

        // save the best product in the session
        request.getSession().setAttribute(bestOptionalProduct, best);
    }

    private void loadPurchasesPerPackage(HttpServletRequest request) {
        // get all purchases per package
        List<String> purchases = salesReportService.getAllPurchasesPerPackage();

        // save purchases in the session
        request.getSession().setAttribute(allPurchasesPerPackage, purchases);
    }

    private void loadPurchasesPerPackageValidity(HttpServletRequest request) {
        // retrieve purchases per package and validity
        List<String> purchasesValidity = salesReportService.getAllPurchasesPerPackageValidity();

        // save purchasesValidity in the session
        request.getSession().setAttribute(allPurchasesPerPackageValidity, purchasesValidity);
    }

    private void loadValuesPerPackageOptionalProduct(HttpServletRequest request) {
        // retrieve values of sales per package with OptionalProducts
        List<String> valuesOptional = salesReportService.getAllValuePerPackageWithOptionalProduct();

        // save values in the session
        request.getSession().setAttribute(allValuesPerPackageOptionalProduct, valuesOptional);
    }

    private void loadValuesPerPackageWithoutOp(HttpServletRequest request) {
        // retrieve values of sales per package with OptionalProducts
        List<String> values = salesReportService.getAllValuePerPackageWithoutOp();

        // save values in the session
        request.getSession().setAttribute(allValuesPerPackageWithoutOp, values);
    }

    private void loadInsolventUsers(HttpServletRequest request) {
        // retrieve insolvent Users
        List<String> users = salesReportService.insolventUsers();

        // save users in the session
        request.getSession().setAttribute(insolventUsers, users);
    }

    private void loadSuspendedOrders(HttpServletRequest request) {
        // retrieve suspended Orders
        List<String> orders = salesReportService.suspendedOrders();

        // save orders in the session
        request.getSession().setAttribute(suspendedOrders, orders);
    }

    private void loadAlerts(HttpServletRequest request) {
        // retrieve active alerts
        List<String> alerts = salesReportService.getAlerts();

        // save alerts in the session
        request.getSession().setAttribute(activeAlerts, alerts);
    }


    private void redirectSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getServletContext().getContextPath() + "/employee/salesReport.jsp");
    }
}
