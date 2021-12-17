package it.polimi.db2.telcoservice_sc42.services;

import java.util.List;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import it.polimi.db2.telcoservice_sc42.entities.Client;

/**
 * Session Bean implementation class LoginService
 */
@Stateless
@LocalBean
public class LoginService {

    @PersistenceContext(unitName = "TelcoService_EJB")
    private EntityManager em;

    /**
     * Default constructor.
     */
    public LoginService() {
        try {
            ;
        } catch (Exception e) {
            System.out.println("LoginService");
            e.printStackTrace();
        }
    }

    public Client checkCredentials(String usrn, String pwd) {
        List<Client> uList = null;

        try {
            uList = em.createNamedQuery("Client.withCredentials", Client.class).setParameter(1, usrn).setParameter(2, pwd)
                    .getResultList();
        } catch (PersistenceException e) {
            System.out.println("Could not verify credentials");
            System.out.println(e);
            return null;
        }

        System.out.println("DB returned: " + uList);

        if (uList.isEmpty())
            return null;
        else if (uList.size() == 1)
            return uList.get(0);
        System.out.println("More than one user registered with same credentials");
        return null;
    }

}

