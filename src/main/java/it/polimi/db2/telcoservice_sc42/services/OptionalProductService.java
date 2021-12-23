package it.polimi.db2.telcoservice_sc42.services;

import it.polimi.db2.telcoservice_sc42.entities.OptionalProduct;
import it.polimi.db2.telcoservice_sc42.exception.BadlyFormattedOptionalProductException;
import it.polimi.db2.telcoservice_sc42.exception.NegativeFeeException;
import it.polimi.db2.telcoservice_sc42.exception.PastDateException;
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

    public void changeExpirationDate(OptionalProduct optionalProduct, Date newExpirationDate){
        em.find(OptionalProduct.class, optionalProduct).setExpirationDate(newExpirationDate);
        em.persist(optionalProduct);
    }

    public void createOptionalProduct(String name, Float fee, Date expirationDate) throws BadlyFormattedOptionalProductException {
        if ( expirationDate.before(new Date()) ) {
            throw new PastDateException();
        }

        if ( fee < 0 ) {
            throw new NegativeFeeException();
        }

        OptionalProduct optionalProduct = new OptionalProduct(name, fee, expirationDate);
        em.persist(optionalProduct);
    }
}
