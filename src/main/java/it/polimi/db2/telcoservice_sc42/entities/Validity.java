package it.polimi.db2.telcoservice_sc42.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@IdClass(ValidityPrimaryKey.class)
public class Validity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @Id
    @ManyToOne
    @JoinColumn (name = "PACKAGE_ID")
    private ServicePackage servicePackage;

    private Integer period;

    @Column(name = "MONTHLY_FEE")
    private Float monthlyFee;

    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    @OneToMany(mappedBy="validity", fetch=FetchType.LAZY)
    private List<Order> orders;

    public Validity() {
    }

    public Validity(int id, ServicePackage servicePackage, int period, float monthlyFee, Date expirationDate ){
        this.id = id;
        this.setServicePackage(servicePackage);
        this.period = period;
        this.monthlyFee = monthlyFee;
        this.expirationDate = expirationDate;
    }

    public Validity(int id, ServicePackage servicePackage, int period, float monthlyFee ){
        this.id = id;
        this.setServicePackage(servicePackage);
        this.period = period;
        this.monthlyFee = monthlyFee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeriod() {
        return period;
    }
    public void setPeriod(int period) {
        this.period = period;
    }
    public Float getMonthlyFee() {
        return monthlyFee;
    }
    public void setMonthlyFee(Float monthlyFee) {
        this.monthlyFee = monthlyFee;
    }
    public Date getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
