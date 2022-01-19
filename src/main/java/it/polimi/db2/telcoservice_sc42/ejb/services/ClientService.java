package it.polimi.db2.telcoservice_sc42.ejb.services;

import it.polimi.db2.telcoservice_sc42.ejb.entities.Client;
import it.polimi.db2.telcoservice_sc42.exception.ClientNotFoundException;
import it.polimi.db2.telcoservice_sc42.exception.CredentialErrorException;
import it.polimi.db2.telcoservice_sc42.exception.NonUniqueClientException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;

import java.util.List;
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
     * @throws CredentialErrorException if the credentials doesn't satisfy the database's constraints.
     */
    public Client addClient(String username, String email, String password) throws NonUniqueClientException, CredentialErrorException {
        if ( isRegistered(username) ) { throw new NonUniqueClientException(); }
        if ( !isPasswordValid(password) || !isUsernameValid(password) || !isEmailValid(email)) {
            throw new CredentialErrorException();
        }

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
     * @throws CredentialErrorException if the credentials doesn't respect with the database constraints.
     */
    public Client checkCredentials(String username, String pwd) throws ClientNotFoundException, NonUniqueClientException, CredentialErrorException {
        List<Client> uList;

        if ( !isUsernameValid(username) || !isPasswordValid(pwd) ) throw new CredentialErrorException();

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

    private boolean isUsernameValid(String username) {
        return username.length() <= 255 && username.length() > 0;
    }

    private boolean isPasswordValid(String password) {
        return password.length() <= 31 && password.length() > 0;
    }

    private boolean isEmailValid(String email) {
        return email.length() <= 255;
    }

}

