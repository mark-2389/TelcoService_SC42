package it.polimi.db2.telcoservice_sc42.services;

import it.polimi.db2.telcoservice_sc42.entities.Auditing;
import it.polimi.db2.telcoservice_sc42.entities.Client;
import it.polimi.db2.telcoservice_sc42.entities.Order;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class SalesReportService {

    @PersistenceContext(unitName = "TelcoService_EJB")
    private EntityManager em;

    public SalesReportService() {
    }

    /**
     * The method retrieves the aggregate data sales: number of total purchases per package
     * @return a list of Strings: the string representation of the data retrieved from the database
     */
    public List<String> getAllPurchasesPerPackage(){
        List<Object[]> results = em.createNamedQuery("PurchasePerPackage.named", Object[].class).getResultList();
        List<String> stringedResults = new ArrayList<>();

        for (Object[] object : results )
            stringedResults.add("ID: " + object[0] + "    NAME: " + object[1] + "    PURCHASES: " + object[2] );

        return stringedResults;
    }

    /**
     * The method retrieves the aggregate data sales: number of total purchases per package and validity period
     * @return a list of Strings: the string representation of the data retrieved from the database
     */
    public List<String> getAllPurchasesPerPackageValidity(){
        List<Object[]> results = em.createNamedQuery("PurchasePerPackageValidity.named", Object[].class).getResultList();
        List<String> stringedResults = new ArrayList<>();

        for (Object[] object : results )
            stringedResults.add("[ (" + object[0] + ") " + object[2] +  ",    (" + object[1] + ") " + object[3] + " ]    PURCHASES: " + object[4] );

        return stringedResults;
    }

    /**
     * The method retrieves the aggregate data sales: total value of sales per package with the optional products
     * @return a list of Strings: the string representation of the data retrieved from the database
     */
    public List<String> getAllValuePerPackageWithOptionalProduct(){
        List<Object[]> results = em.createNamedQuery("ValuePerPackageWithOptionalProduct.named", Object[].class).getResultList();
        List<String> stringedResults = new ArrayList<>();

        for (Object[] object : results )
            stringedResults.add("ID: " + object[0] + "    NAME: " + object[1] + "    VALUE: " + object[2] );

        return stringedResults;
    }

    /**
     * The method retrieves the aggregate data sales: total value of sales per package without the optional products
     * @return a list of Strings: the string representation of the data retrieved from the database
     */
    public List<String> getAllValuePerPackageWithoutOp(){
        List<Object[]> results = em.createNamedQuery("ValuePerPackageWithoutOp.named", Object[].class).getResultList();
        List<String> stringedResults = new ArrayList<>();

        for (Object[] object : results )
            stringedResults.add("ID: " + object[0] + "    NAME: " + object[1] + "    VALUE: " + object[2] );

        return stringedResults;
    }

    /**
     * The method retrieves the aggregate data sales: average number of optional products sold together with each service package
     * @return a list of Strings: the string representation of the data retrieved from the database
     */
    public List<String> getAllAveragesOptionalProductsPerPackage(){
        List<Object[]> results = em.createNamedQuery("AverageOptionalProductsPerPackage.named", Object[].class).getResultList();
        List<String> stringedResults = new ArrayList<>();

        for (Object[] object : results )
            stringedResults.add("ID: " + object[0] + "    NAME: " + object[1] + "    AVERAGE: " + object[2] );

        return stringedResults;
    }

    /**
     * The method retrieves the aggregate data sales: best seller optional product, i.e. the optional product with the greatest value of sales across all the sold service packages.
     * @return a list of Strings: the string representation of the data retrieved from the database
     */
    public String findBestOptionalProduct(){
        List<Object[]> results = em.createNamedQuery("BestOptionalProduct.getNamed", Object[].class).getResultList();

        if (results.isEmpty()) return null;

        Object[] object = results.get(0);

        return "ID: " + object[0] + "    NAME: " + object[1] + "    VALUE: " + object[2];
    }

    /**
     * The method queries the database so to retrieve the list of insolvent users
     * @return a list of string: the string representation of insolvent users
     */
    public List<String> insolventUsers(){
        List<Client> insolvent = em.createNamedQuery("Client.insolvent", Client.class).getResultList();
        return insolvent.stream().map(Client::toString).collect(Collectors.toList());
    }

    /**
     * The method queries the database so to retrieve the list of suspended orders
     * @return a list of string: the string representation of suspended orders
     */
    public List<String> suspendedOrders(){
        List<Order> suspended = em.createNamedQuery("Order.rejected", Order.class).getResultList();
        return suspended.stream().map(Order::toString).collect(Collectors.toList());
    }

    /**
     * The method queries the database so to retrieve the list of alerts
     * @return a list of string: the string representation of alerts
     */
    public List<String> getAlerts(){
        List<Auditing> alerts = em.createNamedQuery("Auditing.allActive", Auditing.class).getResultList();
        return alerts.stream().map(Auditing::toString).collect(Collectors.toList());
    }
}
