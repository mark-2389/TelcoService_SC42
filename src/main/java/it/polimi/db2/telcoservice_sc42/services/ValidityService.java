package it.polimi.db2.telcoservice_sc42.services;

import it.polimi.db2.telcoservice_sc42.entities.Validity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.util.Date;

@Stateless
public class ValidityService {
    @PersistenceContext(unitName = "TelcoService_EJB")
    private EntityManager em;

    public void changePeriod (Validity validity, int period){
        em.find(Validity.class, validity).setPeriod(period);
        em.persist(validity);
    }
    
    public void changeMonthlyFee(Validity validity, BigDecimal monthlyFee){
        em.find(Validity.class, validity).setMonthlyFee(monthlyFee);
        em.persist(validity);
    }

    public void changeExpirationDate(Validity validity, Date newExpirationDate){
        em.find(Validity.class, validity).setExpirationDate(newExpirationDate);
        em.persist(validity);
    }
}
