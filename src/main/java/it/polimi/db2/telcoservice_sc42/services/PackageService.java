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

    /**
     * The method retrieves the associated servicePackage, given a certain orderId
     * @param orderId the id of the given order
     * @return the servicePackage related to the order
     */
    public ServicePackage findServicePackageByOrder(int orderId){
        Order order = em.find(Order.class, orderId);
        return order.getPackage();
    }

    /**
     * The method retrieves the specific servicePackage with the corresponding id
     * @param packageId the id of the package to be retrieved
     * @return the service package with the correspongin id
     */
    public ServicePackage findServicePackageById(int packageId){
        return em.find(ServicePackage.class, packageId);
    }

    /**
     * The method is invoked when we are creating a new ServicePackage. It provides the functionalities to associate the package to the services it offers.
     * Moreover it all manages to create and set all the validityPeriods for such new servicePackage
     * @param id the id of the servicePackage to be created
     * @param name the name of the servicePackage to be created
     * @param expirationDate the expirationDate of the servicePackage to be created
     * @param services a list of services that the new ServicePackage offers
     * @param periods a list of periods that the new ServicePackage allows
     * @param monthlyFees a list of monthlyFees, each of them corresponds to a period in the same position
     * @param dates a list of expirationDates for each new validity to be created
     */
    public void createServicePackage(int id, String name, Date expirationDate, List<Service> services, List<Integer> periods, List<Float> monthlyFees, List<Date> dates ){
        if ( periods.size() != monthlyFees.size() || monthlyFees.size() != dates.size() )  return;
        int size = periods.size();

        ServicePackage servicePackage = new ServicePackage(id, name, expirationDate);

        servicePackage.setServices(services);

        //for each service it completes the JPA relationship
        for( Service s : services ){
            em.find(Service.class, s).addPackage(servicePackage);
            em.persist(s);
        }

        for (int i = 0; i < size; i++) {
            Validity validity = new Validity(i, servicePackage, periods.get(i), monthlyFees.get(i), dates.get(i));
            servicePackage.addValidity(validity);
            validity.setServicePackage(servicePackage);
            em.persist(validity);
        }
    }

    public void modifyExpirationDate(ServicePackage toModify, Date newDate){
        ServicePackage servicePackage = em.find(ServicePackage.class, toModify);

        toModify.setExpirationDate(newDate);
    }

    public void addService(ServicePackage toModify,Service newService){
        ServicePackage servicePackage = em.find(ServicePackage.class, toModify);

        servicePackage.addService(newService);

        em.persist(servicePackage);
    }

    public void removeService(ServicePackage toModify,Service oldService){
        ServicePackage servicePackage = em.find(ServicePackage.class, toModify);

        servicePackage.removeService(oldService);

        em.persist(servicePackage);
    }

    public void addValidity(ServicePackage toModify,int period, float monthlyFee, Date date){
        ServicePackage servicePackage = em.find(ServicePackage.class, toModify);
        //TODO check how to set id correctly for new instances
        Validity validity = new Validity(0, servicePackage, period, monthlyFee, date);

        servicePackage.addValidity(validity);
        validity.setServicePackage(servicePackage);

        em.persist(validity);
    }

    public void removeValidity(ServicePackage toModify,Validity oldValidity){
        ServicePackage servicePackage = em.find(ServicePackage.class, toModify);
        Validity validity = em.find(Validity.class, oldValidity);

        servicePackage.removeValidity(validity);
        //TODO how to handle the remove of the validity? New column for unavilability?

        em.persist(validity);
    }

}
