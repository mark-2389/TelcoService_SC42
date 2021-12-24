package it.polimi.db2.telcoservice_sc42.entities;

import java.io.Serializable;
import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


@Entity
@Table(name = "`order`")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;


    // TODO: @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Id
    private Integer id;

    @Column(name = "HOUR_CREATION")
    private Time creationHour;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_CREATION")
    private Date creationDate;

    @Column(name = "NUMBER_REJECTIONS")
    private Integer numberOfRejections;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_SUBSCRIPTION")
    private Date subscriptionDate;

    // TODO double or bigDecimal?
    @Column(name = "TOTAL_COST")
    private Double totalCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_VALID")
    private OrderStatus status;

    // an order refers to one validity period, but the same validity
    // period can be assigned to multiple orders.
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "VALIDITY_ID", referencedColumnName = "ID"),
            @JoinColumn(name = "PACKAGE_ID", referencedColumnName = "PACKAGE_ID", updatable = false, insertable = false )
    })
    private Validity validity;


    // an order refers to a package only, but the same package can be part
    // of multiple orders.
    @ManyToOne
    @JoinColumn(name = "PACKAGE_ID", referencedColumnName = "ID")
    private ServicePackage servicePackage;


    // An order has just one client but a client can do different orders.
    @ManyToOne
    @JoinColumn(name = "CLIENT")
    private Client client;

    public Order() {
        this.id = 0;
        this.creationDate = java.sql.Date.valueOf(LocalDate.now());
        this.creationHour = Time.valueOf(LocalTime.now());
        this.status = OrderStatus.DEFAULT;
        this.numberOfRejections = 0;
    }

    public Order(Client client, Validity validityId, ServicePackage packageId, Date subscriptionDate ) {
        new Order();
        this.client = client;
        this.validity = validityId;
        this.servicePackage = packageId;
        this.subscriptionDate = subscriptionDate;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getHour() {
        return creationHour;
    }

    public void setHour(Time hour) {
        this.creationHour = hour;
    }

    public Date getDate() {
        return creationDate;
    }

    public void setDate(Date date) {
        this.creationDate = date;
    }

    public int getNumberOfRejections() {
        return numberOfRejections;
    }

    public void setNumberOfRejections(int numberOfRejections) {
        this.numberOfRejections = numberOfRejections;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public ServicePackage getPackage() {
        return servicePackage;
    }

    public void setPackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
