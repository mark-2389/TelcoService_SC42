package it.polimi.db2.telcoservice_sc42.services;

import it.polimi.db2.telcoservice_sc42.views.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class SalesReportService {

    @PersistenceContext(unitName = "TelcoService_EJB")
    private EntityManager em;

    public SalesReportService() {
    }

    //Number of total purchases per package.
    public List<PurchasePerPackage> getAllPurchasesPerPackage(){
        return em.createNamedQuery("PurchasePerPackage.all", PurchasePerPackage.class).getResultList();
    }

    //Number of total purchases per package and validity period.
    public List<PurchasePerPackageValidity> getAllPurchasesPerPackageValidity(){
        return em.createNamedQuery("PurchasePerPackageValidity.all", PurchasePerPackageValidity.class).getResultList();
    }

    //Total value of sales per package with the optional products.
    public List<ValuePerPackageWithOptionalProduct> getAllValuePerPackageWithOptionalProduct(){
        return em.createNamedQuery("ValuePerPackageWithOptionalProduct.all", ValuePerPackageWithOptionalProduct.class).getResultList();
    }

    //Total value of sales per package without the optional products.
    public List<ValuePerPackageWithoutOp> getAllValuePerPackageWithoutOp(){
        return em.createNamedQuery("ValuePerPackageWithoutOp.all", ValuePerPackageWithoutOp.class).getResultList();
    }

    //Average number of optional products sold together with each service package.
    public List<AverageOptionalProductsPerPackage> getAllAveragesOptionalProductsPerPackage(){
        return em.createNamedQuery("AverageOptionalProductsPerPackage.all", AverageOptionalProductsPerPackage.class).getResultList();
    }

    //Best seller optional product, i.e. the optional product with the greatest value of sales across all the sold service packages.
    public BestOptionalProduct findBestOptionalProduct(){
        return em.createNamedQuery("BestOptionalProduct.get", BestOptionalProduct.class).getSingleResult();
    }

    //List of insolvent users, suspended orders and alerts.

}
