package it.polimi.db2.telcoservice_sc42.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.persistence.Enumerated;

import java.util.List;

@Entity
@NamedQuery(name = "Client.withCredentials", query = "SELECT r FROM Client r  WHERE r.username = ?1 and r.password = ?2")
@NamedQuery(name = "Client.insolvent", query = "SELECT c FROM Client c WHERE c.insolvent = it.polimi.db2.telcoservice_sc42.entities.UserStatus.INSOLVENT" )
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    // TODO: consider adding an id.

    @Id
    private String username;
    private String password;
    private String email;

    @Column(name = "NUMBER_REJECTIONS")
    private Integer numberOfRejections;

    @Column(columnDefinition = "ENUM('SOLVENT', 'INSOLVENT')")
    @Enumerated(EnumType.STRING)
    private UserStatus insolvent;

    // A client can do many orders.
    @OneToMany(mappedBy="client", fetch=FetchType.LAZY)
    private List<Order> orders;

    public Client() {
        this.numberOfRejections = 0;
        this.insolvent = UserStatus.SOLVENT;
    }

    public Client(String username, String email, String password) {
        this();
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Client{" +
                "username='" + username + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumberOfRejections() {
        return numberOfRejections;
    }

    public void setNumberOfRejections(int numberOfRejections) {
        this.numberOfRejections = numberOfRejections;
    }

    public UserStatus getInsolvent() {
        return insolvent;
    }

    public void setInsolvent(UserStatus insolvent) {
        this.insolvent = insolvent;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order){
        getOrders().add(order);
        order.setClient(this);
    }

    public void removeOrder(Order order){
        getOrders().remove(order);
    }
}
