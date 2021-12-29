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

    public LoadSalesReportServlet() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ENTERING LOAD REPORT SERVLET");

        loadAverageOptionalProduct(request);
        loadBestOptionalProduct(request);
        loadPurchasesPerPackage(request);
        loadPurchasesPerPackageValidity(request);
        loadValuesPerPackageOptionalProduct(request);
        loadValuesPerPackageWithoutOp(request);

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
        List<ValuePerPackageWithOptionalProduct> valuesOptional = salesReportService.getAllValuePerPackageWithOptionalProduct();
        System.out.println("5" + valuesOptional);

        // save values in the session
        request.getSession().setAttribute(allValuesPerPackageOptionalProduct, valuesOptional);
    }

    private void loadValuesPerPackageWithoutOp(HttpServletRequest request) {
        // retrieve values of sales per package with OptionalProducts
        List<ValuePerPackageWithoutOp> values = salesReportService.getAllValuePerPackageWithoutOp();
        System.out.println("6" + values);

        // save values in the session
        request.getSession().setAttribute(allValuesPerPackageWithoutOp, values);
    }


    private void redirectSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getServletContext().getContextPath() + "/employee/salesReport.jsp");
    }
}
