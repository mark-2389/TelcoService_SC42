package it.polimi.db2.telcoservice_sc42.services;

import it.polimi.db2.telcoservice_sc42.entities.MobilePhoneService;
import it.polimi.db2.telcoservice_sc42.entities.InternetService;
import it.polimi.db2.telcoservice_sc42.entities.Service;
import it.polimi.db2.telcoservice_sc42.entities.ServiceType;
import it.polimi.db2.telcoservice_sc42.exception.BadParametersException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Arrays;
import java.util.Date;

@Stateless
public class ServiceService {
    @PersistenceContext(name="TelcoService_EJB")
    EntityManager entityManager;

    public ServiceService(){ }

    public Service createService(ServiceType type, Date expirationDate, Float gbFee, Integer gbs, Float smsFee, Integer sms, Float callFee, Integer minutes) throws BadParametersException {

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

        entityManager.persist(service);
        return  service;
    }

    private boolean areParametersValid(ServiceType type, Date expirationDate, Float gbFee, Integer gbs, Float smsFee, Integer sms, Float callFee, Integer minutes) {
        return Arrays.stream(ServiceType.values()).anyMatch(t -> t == type);
    }

    public Service createFixedPhoneService(Date expirationDate) {
        return new Service(ServiceType.FIXED_PHONE, expirationDate);
    }

    public Service createMobilePhoneService(Date expirationDate, Float smsFee, Integer sms, Float callFee, Integer minutes) {
        return new MobilePhoneService(expirationDate, minutes, callFee, sms, smsFee);
    }

    public Service createMobileInternetService(Date expirationDate, Float gbFee, Integer gbs) {
        return new InternetService(expirationDate, gbs, gbFee, true);
    }

    public Service createFixedInternetService(Date expirationDate, Float gbFee, Integer gbs) {
        return new InternetService(expirationDate, gbs, gbFee, false);
    }
}