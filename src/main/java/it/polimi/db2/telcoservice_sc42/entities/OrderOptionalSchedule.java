package it.polimi.db2.telcoservice_sc42.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "optional_schedule")
@IdClass(OrderOptionalSchedulePrimaryKey.class)
public class OrderOptionalSchedule implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne( cascade = CascadeType.PERSIST )
    @JoinColumn (name = "ORDER_ID")
    private Order order;

    @Id
    @ManyToOne ( cascade = CascadeType.PERSIST )
    @JoinColumn (name = "OPTIONAL_PRODUCT_ID")
    private OptionalProduct optional;

    @Column (name = "USERNAME")
    private String username;

    @Column(name = "ACTIVATION_DATE")
    private Date activationDate;

    @Column(name = "DEACTIVATION_DATE")
    private Date deactivationDate;

    public OrderOptionalSchedule() {
    }

    public Order getOrder() {
        return order;
    }

    public OptionalProduct getOptional() {
        return optional;
    }

    public String getUsername() {
        return username;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public Date getDeactivationDate() {
        return deactivationDate;
    }
}