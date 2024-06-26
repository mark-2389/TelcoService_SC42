package it.polimi.db2.telcoservice_sc42.ejb.services;

import it.polimi.db2.telcoservice_sc42.ejb.entities.FixedInternetService;
import it.polimi.db2.telcoservice_sc42.ejb.entities.MobileInternetService;
import it.polimi.db2.telcoservice_sc42.ejb.entities.MobilePhoneService;
import it.polimi.db2.telcoservice_sc42.ejb.entities.Service;
import it.polimi.db2.telcoservice_sc42.exception.BadParametersException;
import it.polimi.db2.telcoservice_sc42.ejb.enums.ServiceType;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@Stateless
public class ServiceService {
    @PersistenceContext(name="TelcoService_EJB")
    EntityManager entityManager;

    public ServiceService(){ }

    public Service createService(ServiceType type, Date expirationDate, BigDecimal gbFee, Integer gbs, BigDecimal smsFee, Integer sms, BigDecimal callFee, Integer minutes) throws BadParametersException {

        if ( !areParametersValid(type, expirationDate, gbFee, gbs, smsFee, sms, callFee, minutes) ) {
            throw new BadParametersException();
        }

        Service service; 
        
        if ( type == ServiceType.FIXED_PHONE ) {
            service = createFixedPhoneService(expirationDate);
        } else if ( type == ServiceType.MOBILE_PHONE ) {
            service = createMobilePhoneService(expirationDate, smsFee, sms, callFee, minutes);
        } else if ( type == ServiceType.MOBILE_INTERNET ) {
            service = createMobileInternetService(expirationDate, gbFee, gbs);
        } else {
            service = createFixedInternetService(expirationDate, gbFee, gbs);
        }

        if ( service == null ) { throw new BadParametersException(); }

        System.out.println(service.getType().name());

        entityManager.persist(service);
        return service;
    }

    private boolean areParametersValid(ServiceType type, Date expirationDate, BigDecimal gbFee, Integer gbs, BigDecimal smsFee, Integer sms, BigDecimal callFee, Integer minutes) {
        BigDecimal zero = new BigDecimal(0);
        BigDecimal max = new BigDecimal("9999.99");

        return Arrays.stream(ServiceType.values()).anyMatch(t -> t == type) &&
                smallerEqualTo(gbFee, max) && biggerEqualThan(gbFee, zero) &&
                smallerEqualTo(smsFee, max) && biggerEqualThan(smsFee, zero) &&
                smallerEqualTo(callFee, max) && biggerEqualThan(callFee, zero) &&
                gbs >= 0 && sms >= 0 && minutes >= 0;
    }

    private boolean smallerEqualTo(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) <= 0;
    }

    private boolean biggerEqualThan(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) >= 0;
    }

    public Service createFixedPhoneService(Date expirationDate) {
        return new Service(ServiceType.FIXED_PHONE, expirationDate);
    }

    public Service createMobilePhoneService(Date expirationDate, BigDecimal smsFee, Integer sms, BigDecimal callFee, Integer minutes) {
        return new MobilePhoneService(expirationDate, minutes, callFee, sms, smsFee);
    }

    public Service createMobileInternetService(Date expirationDate, BigDecimal gbFee, Integer gbs) {
        return new MobileInternetService(expirationDate, gbs, gbFee);
    }

    public Service createFixedInternetService(Date expirationDate, BigDecimal gbFee, Integer gbs) {
        return new FixedInternetService(expirationDate, gbs, gbFee);
    }

    public List<Service> findValidServices() {
        return entityManager.createNamedQuery("Service.valid", Service.class).getResultList();
    }
}
