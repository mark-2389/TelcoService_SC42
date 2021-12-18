package it.polimi.db2.telcoservice_sc42.entities;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "optional_product")
public class OptionalProduct {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    private String name;

    @Column(name = "monthly_fee")
    private float monthlyFee;

    @Temporal(TemporalType.DATE)
    @Column(name = "expiration_date")
    private Date expirationDate;


    @ManyToMany
    @JoinTable
            (name = "orderOptionalComposition",
                    joinColumns = @JoinColumn(name="optionalProductId"),
                    inverseJoinColumns = @JoinColumn(name="orderId"))
    private Collection<Order> orders;

    @ManyToMany
    @JoinTable
    	(name = "optionalProductComposition",
    	joinColumns = @JoinColumn(name="optionalProductId"),
    	inverseJoinColumns = @JoinColumn(name="packageId"))
    private Collection<ServicePackage> packages;


    @ManyToMany
    @JoinTable
            (name = "optionalsschedule",
                    joinColumns = @JoinColumn(name="optionalProductId"),
                    inverseJoinColumns = @JoinColumn(name="username"))
    private Collection<Client> clients;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(float monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public Collection<Client> getClients() {
        return clients;
    }
}