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

    public List<Order> findOrderByClient(String username) {
        Client client = em.find(Client.class, username);
        return client.getOrders();
    }

    public Order findMissionById(int orderId) {
        return em.find(Order.class, orderId);
    }

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
