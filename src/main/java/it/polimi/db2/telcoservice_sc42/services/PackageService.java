package it.polimi.db2.telcoservice_sc42.services;

import it.polimi.db2.telcoservice_sc42.entities.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
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
     * @return the service package with the corresponding id
     */
    public ServicePackage findServicePackageById(int packageId){
        return em.find(ServicePackage.class, packageId);
    }

    /**
     * Return the valid ServicePackages.
     *
     * A ServicePackage is valid when its expiration_date is in the future (i.e. it's after the current date).
     * @return the list of valid service packages.
     */
    public List<ServicePackage> findValidServicePackages() {
        return em.createNamedQuery("ServicePackage.valid", ServicePackage.class).getResultList();
    }

    /**
     * The method is invoked when we are creating a new ServicePackage. It provides the functionalities to associate the package to the services it offers.
     * Moreover it all manages to create and set all the validityPeriods for such new servicePackage
     * @param name the name of the servicePackage to be created
     * @param expirationDate the expirationDate of the servicePackage to be created
     * @param serviceIds a list of id of the Services that the new ServicePackage offers
     * @param optionalIds a list of id of the OptionalProducts that have to be added to the Package
     */
    public ServicePackage createServicePackage(String name, Date expirationDate, List<Integer> serviceIds, List<Integer> optionalIds, List<IndependentValidityPeriod> periods){
        ServicePackage servicePackage = new ServicePackage(name, expirationDate);
        System.out.println(servicePackage);

        List<Service> services = new ArrayList<>();
        List<OptionalProduct> optionals = new ArrayList<>();

        // get the actual services
        for (int id: serviceIds) {
            services.add(em.find(Service.class, id));
        }

        // get the actual optionals
        for (int id: optionalIds) {
            optionals.add(em.find(OptionalProduct.class, id));
        }

        // add the services to the package
        servicePackage.setServices(services);

        // add the optional products to the package
        servicePackage.setOptionalProducts(optionals);

        // persist the services
        for( Service s : services ){
            // System.out.println("Is service object managed?  " + em.contains(s));
            s.addPackage(servicePackage);
        }

        // persist the optional products
        for( OptionalProduct p: optionals ) {
            // System.out.println("Is optionals object managed?  " + em.contains(s));
            p.addPackage(servicePackage);
        }

        // persist to get the save the servicePackage and get its id
        em.persist(servicePackage);
        em.flush();



        //// add the validities
        //List<Validity> validities = periods.stream()
        //        .map(p -> p.getValidityWith(servicePackage))
        //        .collect(Collectors.toList());
        //for (Validity v: validities) {
        //    addValidity(servicePackage.getId(), v);
        //}

        List<Validity> validities = new ArrayList<>();

        //get all Validities
        for (IndependentValidityPeriod period : periods ){
            System.out.println("WHEN ADDING VALIDITIES: " + em.contains(servicePackage) );
            validities.add( period.getValidityWith(servicePackage));
        }

        servicePackage.setValidities(validities);

        for (Validity v : validities){
            System.out.println("WHEN SETTING SERVICE PACKAGE: " + em.contains(v) );
            //v.setServicePackage(servicePackage);
            em.persist(v);
            System.out.println("WHEN AFTER SETTING SERVICE PACKAGE: " + em.contains(v) );
        }

        System.out.println(validities);

        return servicePackage;
    }

    public void modifyExpirationDate(int toModifyId, Date newDate){
        ServicePackage servicePackage = findServicePackageById(toModifyId);

        servicePackage.setExpirationDate(newDate);
    }

    public void addService(int toModifyId,Service newService){
        ServicePackage servicePackage = findServicePackageById(toModifyId);

        servicePackage.addService(newService);

        em.persist(servicePackage);
    }

    public void removeService(int toModifyId,Service oldService){
        ServicePackage servicePackage = findServicePackageById(toModifyId);

        servicePackage.removeService(oldService);

        em.persist(servicePackage);
    }

    public void addOptionalProduct(ServicePackage toModify,Service newService){
        ServicePackage servicePackage = em.find(ServicePackage.class, toModify);

        servicePackage.addService(newService);

        em.persist(servicePackage);
    }

    public void removeOptionalProduct(int toModifyId,Service oldService){
        ServicePackage servicePackage = findServicePackageById(toModifyId);

        servicePackage.removeService(oldService);

        em.persist(servicePackage);
    }

    public void addValidity(int toModifyId, Validity validity){
        ServicePackage servicePackage = findServicePackageById(toModifyId);
        em.refresh(servicePackage);

        servicePackage.addValidity(validity);
        validity.setServicePackage(servicePackage);

        em.persist(validity);
    }

    public void removeValidity(int toModifyId,int oldValidityId){
        ServicePackage servicePackage = findServicePackageById(toModifyId);
        Validity validity = em.find(Validity.class, oldValidityId);

        servicePackage.removeValidity(validity);
        //TODO how to handle the remove of the validity? New column for unavailability?

        em.persist(validity);
    }

}
