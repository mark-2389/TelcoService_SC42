package it.polimi.db2.telcoservice_sc42.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Time;


@Entity
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;


    // TODO: @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Id
    private Integer id;

    @Column(name = "hourcreation")
    private Time creationHour;

    @Column(name = "datecreation")
    private Date creationDate;

    @Column(name = "number_rejection")
    private Integer numberOfRejections;

    @Column(name = "DATESUBSCRPTION")
    private Date subscriptionDate;
    private Double totalCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "isvalid")
    private OrderStatus status;

    // an order refers to one validity period, but the same validity
    // period can be assigned to multiple orders.
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "validityId", referencedColumnName = "id"),
            @JoinColumn(name = "packageId", referencedColumnName = "packageId", updatable = false, insertable = false )
    })
    private Validity validity;


    // an order refers to a package only, but the same package can be part
    // of multiple orders.
    @ManyToOne
    @JoinColumn(name = "packageId", referencedColumnName = "Id")
    private ServicePackage servicePackage;


    // An order has just one client but a client can do different orders.
    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;



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
}
