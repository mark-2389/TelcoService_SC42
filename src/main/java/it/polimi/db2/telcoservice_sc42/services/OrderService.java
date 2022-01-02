package it.polimi.db2.telcoservice_sc42.services;


import it.polimi.db2.telcoservice_sc42.entities.*;
import it.polimi.db2.telcoservice_sc42.exception.ClientNotCorrespondingException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class OrderService {
    @PersistenceContext(unitName = "TelcoService_EJB")
    private EntityManager em;

    @EJB(name = "it.polimi.db2.telcoservice_sc42.services/ValidityService")
    ValidityService validityService;

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
    public List<Order> findOrdersByClient(String username) {
        Client client = em.find(Client.class, username);
        return client.getOrders();
    }

    /**
     * Return the rejected orders given a username.
     * @param username the username of the Client.
     * @return the list of rejected orders of the Client.
     */
    public List<Order> findRejectedOrdersByClient(String username) {
        if ( username == null ) return new ArrayList<>();

        List<Order> orders = findOrdersByClient(username);

        return orders.stream()
                .filter( o -> o.getStatus() == OrderStatus.REJECTED )
                .collect(Collectors.toList());
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
     * @param clientUsername the username of the costumer who purchases the order
     * @param validityId the id of the chosen validity period
     * @param packageId the id of the chosen servicePackage to be bought
     * @param dateSubscription the date of the activation of the servicePackage
     * @return the id of the order that has been created, null if some error occurred
     */
    public Integer createOrder (String clientUsername, int validityId, int packageId, Date dateSubscription ) {
        Client costumer = em.find(Client.class, clientUsername);
        ServicePackage servicePackage = em.find(ServicePackage.class, packageId);
        Validity validity = validityService.findValidityByKey(validityId, packageId);

        Order order = new Order( costumer, validity, servicePackage, dateSubscription );

        // for debugging: let's check if mission is managed
        System.out.println("Method createOrder before client.addMission(mission)");
        System.out.println("Is order object managed?  " + em.contains(order));

        costumer.addOrder(order); // updates both sides of the relationship
        servicePackage.addOrder(order); // updates both sides of the relationship

        em.persist(order);
        em.flush();

        // for debugging: let's check if mission is managed
        System.out.println("Method createOrder AFTER client.addMission(mission)");
        System.out.println("Is order object managed?  " + em.contains(order));

        // em.persist(costumer); // makes also order object managed via cascading
        // em.persist(servicePackage); // makes also order object managed via cascading

        System.out.println("Method createOrder after em.persist()");
        System.out.println("Is order object managed?  " + em.contains(order));

        return order.getId();
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

    public void setOrderStatus(int id, OrderStatus status) {
        Order order = em.find(Order.class, id);

        if ( order == null ) return;

        order.setStatus(status);
    }
}
