package it.polimi.db2.telcoservice_sc42.services;

import it.polimi.db2.telcoservice_sc42.entities.OptionalProduct;
import it.polimi.db2.telcoservice_sc42.exception.BadlyFormattedOptionalProductException;
import it.polimi.db2.telcoservice_sc42.exception.NegativeFeeException;
import it.polimi.db2.telcoservice_sc42.exception.PastDateException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Stateless
public class OptionalProductService {
    @PersistenceContext(unitName = "TelcoService_EJB")
    private EntityManager em;

    public void changeMonthlyFee(OptionalProduct optionalProduct, BigDecimal monthlyFee){
        em.find(OptionalProduct.class, optionalProduct).setMonthlyFee(monthlyFee);
        em.persist(optionalProduct);
    }

    public void changeExpirationDate(OptionalProduct optionalProduct, Date newExpirationDate){
        em.find(OptionalProduct.class, optionalProduct).setExpirationDate(newExpirationDate);
        em.persist(optionalProduct);
    }

    public OptionalProduct createOptionalProduct(String name, BigDecimal fee, Date expirationDate) throws BadlyFormattedOptionalProductException {
        if ( expirationDate.before(new Date()) ) {
            throw new PastDateException();
        }

        //the compareTo method return -1, 0, 1 if the fee is numerical less than, equal or greater than BigDecimal.Zero
        //For more information check the BigDecimal's javadoc
        if ( fee.compareTo(BigDecimal.ZERO) < 0 ) {
            throw new NegativeFeeException();
        }

        OptionalProduct optionalProduct = new OptionalProduct(name, fee, expirationDate);
        em.persist(optionalProduct);

        return optionalProduct;
    }

    public OptionalProduct findOptionalProductById(int id) {
        return em.find(OptionalProduct.class, id);
    }

    public List<OptionalProduct> findAllOptionalProducts() {
        return em.createNamedQuery("OptionalProduct.all", OptionalProduct.class).getResultList();
    }

    public List<OptionalProduct> findValidOptionalProducts() {
        return em.createNamedQuery("OptionalProduct.valid", OptionalProduct.class).getResultList();
    }
}
