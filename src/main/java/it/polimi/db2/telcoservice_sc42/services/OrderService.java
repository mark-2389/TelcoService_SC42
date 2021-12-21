package it.polimi.db2.telcoservice_sc42.services;


import it.polimi.db2.telcoservice_sc42.entities.Client;
import it.polimi.db2.telcoservice_sc42.entities.Order;
import it.polimi.db2.telcoservice_sc42.entities.ServicePackage;
import it.polimi.db2.telcoservice_sc42.entities.Validity;
import it.polimi.db2.telcoservice_sc42.exception.ClientNotCorrespondingException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Date;
import java.util.List;

@Stateless
public class OrderService {
    @PersistenceContext(unitName = "TelcoService_EJB")
    private EntityManager em;

    /**
     * Default constructor.
     */
    public OrderService() {
    }

    /**
     * Find orders given a username
     * @param username the username of the Client
     * @return the list of orders of the client
     */
    public List<Order> findOrderByClient(String username) {
        Client client = em.find(Client.class, username);
        return client.getOrders();
    }

    /**
     * Find a specific order given by an id
     * @param orderId the id of the order to retrieve
     * @return the order retrieved from the database
     */
    public Order findOrderById(int orderId) {
        return em.find(Order.class, orderId);
    }

    /**
     * The method creates a new order
     * @param client the costumer who purchases the order
     * @param validityId the chosen validity period
     * @param packageId the chosen servicePackage to be bought
     * @param dateSubscription the date of the activation of the servicePackage
     */
    public void createOrder (Client client, Validity validityId, ServicePackage packageId, Date dateSubscription ) {
        Client costumer = em.find(Client.class, client);
        Order order = new Order( costumer, validityId, packageId, dateSubscription );

        // for debugging: let's check if mission is managed
        System.out.println("Method createOrder before client.addMission(mission)");
        System.out.println("Is order object managed?  " + em.contains(order));

        client.addOrder(order); // updates both sides of the relationship

        // for debugging: let's check if mission is managed
        System.out.println("Method createOrder AFTER client.addMission(mission)");
        System.out.println("Is order object managed?  " + em.contains(order));

        em.persist(client); // makes also order object managed via cascading

        System.out.println("Method createOrder after em.persist()");
        System.out.println("Is order object managed?  " + em.contains(order));

    }

    /**
     * This method allows to delete an order
     * @param orderId the id of the order to be deleted
     * @param client the client who is withdrawing the order
     * @throws ClientNotCorrespondingException the order was not made by the client so there is not any correspondence
     */
    public void deleteOrder(int orderId, Client client) throws ClientNotCorrespondingException {
        Order order = em.find(Order.class, orderId);
        Client costumer = em.find(Client.class, client);

        if (order.getClient() != costumer) {
            throw new ClientNotCorrespondingException("Reporter not authorized to delete this mission");
        }
        client.removeOrder(order); // this updates both directions of the associations

        em.remove(order);
    }
}
