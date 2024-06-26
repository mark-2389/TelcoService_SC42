package it.polimi.db2.telcoservice_sc42.ejb.services;

import it.polimi.db2.telcoservice_sc42.ejb.entities.Validity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ValidityService {
    @PersistenceContext(unitName = "TelcoService_EJB")
    private EntityManager em;

    public Validity findValidityById(int validityId) {
        List<Validity> validities = em.createQuery("SELECT V FROM Validity V WHERE V.id = ?1", Validity.class)
                .setParameter(1, validityId)
                .getResultList();

        if ( validities.isEmpty() ) {
            System.out.println("No validity returned from query");
            return null;
        }

        return validities.get(0);
    }

    public Validity findValidityByKey(int validityId, int packageId) {
        List<Validity> validities = em.createQuery("SELECT V FROM Validity V WHERE V.id = ?1 AND V.servicePackage.id = ?2", Validity.class)
                .setParameter(1, validityId).setParameter(2, packageId)
                .getResultList();

        if ( validities.isEmpty() ) {
            System.out.println("No validity returned from query");
            return null;
        }

        return validities.get(0);
    }
}
