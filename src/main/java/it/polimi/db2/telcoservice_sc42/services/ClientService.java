package it.polimi.db2.telcoservice_sc42.services;

import java.util.List;

import it.polimi.db2.telcoservice_sc42.exception.ClientNotFoundException;
import it.polimi.db2.telcoservice_sc42.exception.NonUniqueClientException;
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
public class ClientService {

    @PersistenceContext(unitName = "TelcoService_EJB")
    private EntityManager em;

    /**
     * Default constructor.
     */
    public ClientService() {
    }

    /**
     * Add a client to the database if it doesn't already exists.
     *
     * @param username the username of the Client.
     * @param email the mail of the Client.
     * @param password the password of the Client.
     * @return the client added to the database.
     * @throws NonUniqueClientException if the client already exists.
     */
    public Client addClient(String username, String email, String password) throws NonUniqueClientException {
        if ( isRegistered(username) ) { throw new NonUniqueClientException(); }

        Client newClient = new Client(username, email, password);
        em.persist(newClient);

        return newClient;
    }

    /**
     * Check if the Client with the given username is already registered.
     * @param username the username of the Client.
     * @return true if the Client is already registered, false otherwise.
     */
    public Boolean isRegistered(String username) {
        List<Client> clients = em.createQuery("SELECT c FROM Client c WHERE c.username = ?1", Client.class)
                                 .setParameter(1, username)
                                 .getResultList();

        return !clients.isEmpty();
    }

    /**
     * Check if the user with the specified credentials is present in the database.
     *
     * @param username the username of the client.
     * @param pwd the password of the client.
     * @return the specified client if it's found on the database, null otherwise.
     * @throws ClientNotFoundException if the Client with the specified credentials doesn't exist.
     * @throws NonUniqueClientException if more Clients with the specified credentials exist.
     */
    public Client checkCredentials(String username, String pwd) throws ClientNotFoundException, NonUniqueClientException {
        List<Client> uList;

        try {
            uList = em.createNamedQuery("Client.withCredentials", Client.class)
                      .setParameter(1, username).setParameter(2, pwd)
                      .getResultList();
        } catch (PersistenceException e) {
            System.out.println("Could not verify credentials");
            e.printStackTrace();
            throw new ClientNotFoundException("PersistenceException");
        }

        System.out.println("DB returned: " + uList);

        if (uList.isEmpty())
            throw new ClientNotFoundException();
        else if (uList.size() == 1)
            return uList.get(0);
        throw new NonUniqueClientException();
    }

}

