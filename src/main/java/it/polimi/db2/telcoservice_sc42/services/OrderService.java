package it.polimi.db2.telcoservice_sc42.services;


import it.polimi.db2.telcoservice_sc42.entities.*;
import it.polimi.db2.telcoservice_sc42.exception.ClientNotCorrespondingException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.sql.Date;
import java.util.ArrayList;
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
     * @param optionals the list of ids of OptionalProducts bought with the ServicePackage
     * @return the id of the order that has been created, null if some error occurred
     */
    public Integer createOrder (String clientUsername, int validityId, int packageId, Date dateSubscription, List<Integer> optionals ) {
        Client costumer = em.find(Client.class, clientUsername);

        if ( !isNameValid(clientUsername) ) return null;

        ServicePackage servicePackage = em.find(ServicePackage.class, packageId);
        Validity validity = validityService.findValidityByKey(validityId, packageId);

        List<OptionalProduct> optionalProducts = optionals.stream().
                                                    map(id -> em.find(OptionalProduct.class, id)).
                                                        collect(Collectors.toList());

        Order order = new Order( costumer, validity, servicePackage, dateSubscription, optionalProducts );

        costumer.addOrder(order); // updates both sides of the relationship
        servicePackage.addOrder(order); // updates both sides of the relationship

        em.persist(order);

        return order.getId();
    }

    private boolean isNameValid(String name) {
        return name.length() > 0 && name.length() <= 255;
    }

    public void setOrderStatus(int id, OrderStatus status) {
        Order order = em.find(Order.class, id);

        if ( order == null ) return;

        order.setStatus(status);

        if ( status == OrderStatus.REJECTED ) {
            order.incrementNumberOfRejections(1);
        }
    }
}
