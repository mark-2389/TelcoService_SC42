package it.polimi.db2.telcoservice_sc42.services;

import it.polimi.db2.telcoservice_sc42.entities.OptionalProduct;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Date;

@Stateless
public class OptionalProductService {
    @PersistenceContext(unitName = "TelcoService_EJB")
    private EntityManager em;

    public void changeMonthlyFee(OptionalProduct optionalProduct, float monthlyFee){
        em.find(OptionalProduct.class, optionalProduct).setMonthlyFee(monthlyFee);
        em.persist(optionalProduct);
    }

    public void changeExpirationDate(OptionalProduct optionalProduct, Date newExpiraitonDate){
        em.find(OptionalProduct.class, optionalProduct).setExpirationDate(newExpiraitonDate);
        em.persist(optionalProduct);
    }
}
