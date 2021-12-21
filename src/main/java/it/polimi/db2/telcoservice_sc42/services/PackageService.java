package it.polimi.db2.telcoservice_sc42.services;

import it.polimi.db2.telcoservice_sc42.entities.Order;
import it.polimi.db2.telcoservice_sc42.entities.Service;
import it.polimi.db2.telcoservice_sc42.entities.ServicePackage;
import it.polimi.db2.telcoservice_sc42.entities.Validity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Date;
import java.util.List;

public class PackageService {
    @PersistenceContext(unitName = "TelcoService_EJB")
    private EntityManager em;

    public PackageService() {
    }

    public ServicePackage findServicePackageByOrder(int orderId){
        Order order = em.find(Order.class, orderId);
        return order.getPackage();
    }

    public ServicePackage findServicePackageById(int packageId){
        return em.find(ServicePackage.class, packageId);
    }

    public void createServicePackage(int id, String name, Date expirationDate, List<Service> services, List<Integer> periods, List<Float> monthlyFees, List<Date> dates ){
        if ( periods.size() != monthlyFees.size() || monthlyFees.size() != dates.size() )  return;
        int size = periods.size();

        ServicePackage servicePackage = new ServicePackage(id, name, expirationDate);

        servicePackage.setServices(services);

        //for each service it completes the JPA relationship
        for( Service s : services ){
            em.find(Service.class, s).addPackage(servicePackage);
        }

        for (int i = 0; i < size; i++) {
            Validity validity = new Validity(i, servicePackage, periods.get(i), monthlyFees.get(i), dates.get(i));
            servicePackage.addValidity(validity);
            validity.setServicePackage(servicePackage);
            em.persist(validity);
        }
    }
    
}
