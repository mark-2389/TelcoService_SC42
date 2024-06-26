package it.polimi.db2.telcoservice_sc42.ejb.services;

import it.polimi.db2.telcoservice_sc42.ejb.entities.OptionalProduct;
import it.polimi.db2.telcoservice_sc42.ejb.entities.Service;
import it.polimi.db2.telcoservice_sc42.ejb.entities.ServicePackage;
import it.polimi.db2.telcoservice_sc42.ejb.entities.Validity;
import it.polimi.db2.telcoservice_sc42.exception.BadParametersException;
import it.polimi.db2.telcoservice_sc42.exception.InvalidChoiceServiceException;
import it.polimi.db2.telcoservice_sc42.utils.IndependentValidityPeriod;
import it.polimi.db2.telcoservice_sc42.ejb.enums.ServiceType;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
    public ServicePackage createServicePackage(String name, Date expirationDate, List<Integer> serviceIds, List<Integer> optionalIds, List<IndependentValidityPeriod> periods) throws InvalidChoiceServiceException, BadParametersException {
        if ( !isNameValid(name) ) {
            throw new BadParametersException();
        }

        ServicePackage servicePackage = new ServicePackage(name, expirationDate);
        List<Service> services = new ArrayList<>();
        List<OptionalProduct> optionals = new ArrayList<>();

        // get the actual services
        for (int id: serviceIds) {
            services.add(em.find(Service.class, id));
        }

        checkCorrectnessServices(services);


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
        //em.persist(servicePackage);
        //em.flush();

        List<Validity> validities = new ArrayList<>();

        //get all Validities
        for (IndependentValidityPeriod period : periods ){
            validities.add( period.getValidityWith(servicePackage));
        }

        servicePackage.setValidities(validities);

        for (Validity v : validities){
            v.setServicePackage(servicePackage);
            //em.persist(v);
        }

        em.persist(servicePackage);
        System.out.println(validities);

        return servicePackage;
    }

    private boolean isNameValid(String name) {
        return name.length() > 0 && name.length() <= 255;
    }

    private void checkCorrectnessServices(List<Service> services) throws InvalidChoiceServiceException {
        List<ServiceType> types = services.stream().map(Service::getType).collect(Collectors.toList());

        for(ServiceType t : ServiceType.values())
            if ( types.stream().filter(s -> s.equal(t.description())).count() > 1 )
                throw new InvalidChoiceServiceException("Cannot choose two or more services of the same type");

    }
}
