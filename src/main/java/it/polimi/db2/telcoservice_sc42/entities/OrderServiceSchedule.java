package it.polimi.db2.telcoservice_sc42.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "service_schedule")
@IdClass(OrderServiceSchedulePrimaryKey.class)
public class OrderServiceSchedule implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne ( cascade = CascadeType.PERSIST )
    @JoinColumn (name = "ORDER_ID")
    private Order order;

    @Id
    @ManyToOne ( cascade = CascadeType.PERSIST )
    @JoinColumn (name = "SERVICE_ID")
    private Service service;

    @Id
    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn (name = "USERNAME")
    private Client client;

    @Column(name = "ACTIVATION_DATE")
    private Date activationDate;

    @Column(name = "DEACTIVATION_DATE")
    private Date deactivationDate;


    public OrderServiceSchedule() {
    }

    public Order getOrder() {
        return order;
    }

    public Service getService() {
        return service;
    }

    public Client getClient() {
        return client;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public Date getDeactivationDate() {
        return deactivationDate;
    }

    @Override
    public String toString() {
        return "OrderServiceSchedule{" +
                "order=" + order +
                ", service=" + service +
                ", client=" + client +
                ", activationDate=" + activationDate +
                ", deactivationDate=" + deactivationDate +
                '}';
    }
}