package it.polimi.db2.telcoservice_sc42.services;

import it.polimi.db2.telcoservice_sc42.entities.Auditing;
import it.polimi.db2.telcoservice_sc42.entities.Client;
import it.polimi.db2.telcoservice_sc42.entities.Order;
import jakarta.ejb.Stateless;
import jakarta.persistence.CacheStoreMode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.QueryHint;

import java.util.*;

@Stateless
public class SalesReportService {

    @PersistenceContext(unitName = "TelcoService_EJB")
    private EntityManager em;

    public SalesReportService() {
    }

    /**
     * The method retrieves the aggregate data sales: number of total purchases per package
     * @return a list of Map<String, String>: the value is the string representation of the data retrieved from the database
     */
    public List<Map<String, String>> getAllPurchasesPerPackage(){
        List<Object[]> results = em.createNamedQuery("PurchasePerPackage.named", Object[].class).getResultList();
        List<Map<String, String>> formattedResults = new ArrayList<>();

        //for each array we create an HashMap to bette organize the retrieved attributes
        for (Object[] object : results ){
            Map<String, String> temp = new HashMap<>();
            temp.put("ID", object[0].toString());
            temp.put("NAME", object[1].toString());
            temp.put("PURCHASES", object[2].toString());
            formattedResults.add(temp);
        }

        return formattedResults;
    }

    /**
     * The method retrieves the aggregate data sales: number of total purchases per package and validity period
     * @return a list of Strings: the string representation of the data retrieved from the database
     */
    public List<Map<String, String>> getAllPurchasesPerPackageValidity(){
        List<Object[]> results = em.createNamedQuery("PurchasePerPackageValidity.named", Object[].class).getResultList();
        List<Map<String, String>> formattedResults = new ArrayList<>();

        //for each array we create an HashMap to bette organize the retrieved attributes
        for (Object[] object : results ){
            Map<String, String> temp = new HashMap<>();
            temp.put("PACKAGEID", object[0].toString());
            temp.put("PACKAGENAME", object[2].toString());
            temp.put("VALIDITYID", object[1].toString());
            temp.put("PERIOD", object[3].toString());
            temp.put("PURCHASES", object[4].toString());
            formattedResults.add(temp);
        }

        return formattedResults;
    }

    /**
     * The method retrieves the aggregate data sales: total value of sales per package with the optional products
     * @return a list of Strings: the string representation of the data retrieved from the database
     */
    public List<Map<String, String>> getAllValuePerPackageWithOptionalProduct(){
        List<Object[]> results = em.createNamedQuery("ValuePerPackageWithOptionalProduct.named", Object[].class).getResultList();
        List<Map<String, String>> formattedResults = new ArrayList<>();

        //for each array we create an HashMap to bette organize the retrieved attributes
        for (Object[] object : results ){
            Map<String, String> temp = new HashMap<>();
            temp.put("ID", object[0].toString());
            temp.put("NAME", object[1].toString());
            temp.put("VALUE", object[2].toString());
            formattedResults.add(temp);
        }

        return formattedResults;
    }

    /**
     * The method retrieves the aggregate data sales: total value of sales per package without the optional products
     * @return a list of Strings: the string representation of the data retrieved from the database
     */
    public List<Map<String, String>> getAllValuePerPackageWithoutOp(){
        List<Object[]> results = em.createNamedQuery("ValuePerPackageWithoutOp.named", Object[].class).getResultList();
        List<Map<String, String>> formattedResults = new ArrayList<>();

        //for each array we create an HashMap to bette organize the retrieved attributes
        for (Object[] object : results ){
            Map<String, String> temp = new HashMap<>();
            temp.put("ID", object[0].toString());
            temp.put("NAME", object[1].toString());
            temp.put("VALUE", object[2].toString());
            formattedResults.add(temp);
        }

        return formattedResults;
    }

    /**
     * The method retrieves the aggregate data sales: average number of optional products sold together with each service package
     * @return a list of Strings: the string representation of the data retrieved from the database
     */
    public List<Map<String, String>> getAllAveragesOptionalProductsPerPackage(){
        List<Object[]> results = em.createNamedQuery("AverageOptionalProductsPerPackage.named", Object[].class).getResultList();
        List<Map<String, String>> formattedResults = new ArrayList<>();

        //for each array we create an HashMap to bette organize the retrieved attributes
        for (Object[] object : results ){
            Map<String, String> temp = new HashMap<>();
            temp.put("ID", object[0].toString());
            temp.put("NAME", object[1].toString());
            temp.put("AVERAGE", object[2].toString());
            formattedResults.add(temp);
        }

        return formattedResults;
    }

    /**
     * The method retrieves the aggregate data sales: best seller optional product, i.e. the optional product with the greatest value of sales across all the sold service packages.
     * @return a list of Strings: the string representation of the data retrieved from the database
     */
    public Map<String, String> findBestOptionalProduct(){
        List<Object[]> results = em.createNamedQuery("BestOptionalProduct.getNamed", Object[].class).getResultList();

        if (results.isEmpty()) return null;

        Object[] object = results.get(0);

        Map<String, String> formattedResult = new HashMap<>();

        formattedResult.put("ID", object[0].toString());
        formattedResult.put("NAME", object[1].toString());
        formattedResult.put("VALUE", object[2].toString());

        return formattedResult;
    }

    /**
     * The method queries the database so to retrieve the list of insolvent users
     * @return a list of string: the string representation of insolvent users
     */
    public List<Map<String, String>> insolventUsers(){
        //"jakarta.persistence.cache.storeMode"
        List<Client> insolvent = em.createNamedQuery("Client.insolvent", Client.class).setHint("jakarta.persistence.cache.storeMode", CacheStoreMode.REFRESH).getResultList();
        List<Map<String, String>> formattedResults = new ArrayList<>();

        //for each array we create an HashMap to better organize the retrieved attributes
        for (Client c : insolvent ){
            Map<String, String> temp = new HashMap<>();
            temp.put("USERNAME", c.getUsername());
            temp.put("EMAIL", c.getEmail());
            temp.put("REJECTIONS", String.valueOf(c.getNumberOfRejections()));
            formattedResults.add(temp);
        }

        return formattedResults;
    }

    /**
     * The method queries the database so to retrieve the list of suspended orders
     * @return a list of string: the string representation of suspended orders
     */
    public List<Map<String, String>> suspendedOrders(){
        List<Order> suspended = em.createNamedQuery("Order.rejected", Order.class).getResultList();
        List<Map<String, String>> formattedResults = new ArrayList<>();

        //for each array we create an HashMap to bette organize the retrieved attributes
        for (Order o : suspended ){
            Map<String, String> temp = new HashMap<>();
            temp.put("ORDER_ID", String.valueOf(o.getId()));
            temp.put("CREATION_DATE", String.valueOf(o.getDate()));
            temp.put("CREATION_HOUR", String.valueOf(o.getHour()));
            temp.put("DATE_SUBSCRIPTION", String.valueOf(o.getSubscriptionDate()));
            temp.put("TOTAL_COST", String.valueOf(o.getTotalCost()));
            formattedResults.add(temp);
        }

        return formattedResults;
    }

    /**
     * The method queries the database so to retrieve the list of alerts
     * @return a list of string: the string representation of alerts
     */
    public List<Map<String, String>> getAlerts(){
        List<Auditing> alerts = em.createNamedQuery("Auditing.allActive", Auditing.class).getResultList();
        List<Map<String, String>> formattedResults = new ArrayList<>();

        //for each array we create an HashMap to bette organize the retrieved attributes
        for (Auditing a : alerts ){
            Map<String, String> temp = new HashMap<>();
            temp.put("USERNAME", a.getUsername());
            temp.put("EMAIL", a.getEmail());
            temp.put("AMOUNT", String.valueOf(a.getAmount()));
            temp.put("REJECTION_DATE", String.valueOf(a.getRejectionDate()));
            temp.put("REJECTION_TIME", String.valueOf(a.getRejectionTime()));
            formattedResults.add(temp);
        }

        return formattedResults;
    }
}
