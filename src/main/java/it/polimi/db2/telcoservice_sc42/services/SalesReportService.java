package it.polimi.db2.telcoservice_sc42.services;

import it.polimi.db2.telcoservice_sc42.views.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class SalesReportService {

    @PersistenceContext(unitName = "TelcoService_EJB")
    private EntityManager em;

    public SalesReportService() {
    }

    //Number of total purchases per package.
    public List<String> getAllPurchasesPerPackage(){
        List<Object[]> results = em.createNamedQuery("PurchasePerPackage.named").getResultList();
        List<String> stringedResults = new ArrayList<>();

        for (Object[] object : results )
            stringedResults.add("ID: " + object[0] + "    NAME: " + object[1] + "    PURCHASES: " + object[2] );

        return stringedResults;
    }

    //Number of total purchases per package and validity period.
    public List<String> getAllPurchasesPerPackageValidity(){
        List<Object[]> results = em.createNamedQuery("PurchasePerPackageValidity.named").getResultList();
        List<String> stringedResults = new ArrayList<>();

        for (Object[] object : results )
            stringedResults.add("[ (" + object[0] + ") " + object[2] +  ",    (" + object[1] + ") " + object[3] + " ]    PURCHASES: " + object[4] );

        return stringedResults;
    }

    //Total value of sales per package with the optional products.
    public List<String> getAllValuePerPackageWithOptionalProduct(){
        List<Object[]> results = em.createNamedQuery("ValuePerPackageWithOptionalProduct.named").getResultList();
        List<String> stringedResults = new ArrayList<>();

        for (Object[] object : results )
            stringedResults.add("ID: " + object[0] + "    NAME: " + object[1] + "    VALUE: " + object[2] );

        return stringedResults;
    }

    //Total value of sales per package without the optional products.
    public List<String> getAllValuePerPackageWithoutOp(){
        List<Object[]> results = em.createNamedQuery("ValuePerPackageWithoutOp.named").getResultList();
        List<String> stringedResults = new ArrayList<>();

        for (Object[] object : results )
            stringedResults.add("ID: " + object[0] + "    NAME: " + object[1] + "    VALUE: " + object[2] );

        return stringedResults;
    }

    //Average number of optional products sold together with each service package.
    public List<String> getAllAveragesOptionalProductsPerPackage(){
        List<Object[]> results = em.createNamedQuery("AverageOptionalProductsPerPackage.named").getResultList();
        List<String> stringedResults = new ArrayList<>();

        for (Object[] object : results )
            stringedResults.add("ID: " + object[0] + "    NAME: " + object[1] + "    AVERAGE: " + object[2] );

        return stringedResults;
    }

    //Best seller optional product, i.e. the optional product with the greatest value of sales across all the sold service packages.
    public String findBestOptionalProduct(){
        List<Object[]> results = em.createNamedQuery("BestOptionalProduct.getNamed").getResultList();

        if (results.isEmpty()) return null;

        Object[] object = results.get(0);

        return "ID: " + object[0] + "    NAME: " + object[1] + "    VALUE: " + object[2];
    }

    // TODO List of insolvent users
    // TODO List of suspended orders
    // TODO List of alerts.

}
