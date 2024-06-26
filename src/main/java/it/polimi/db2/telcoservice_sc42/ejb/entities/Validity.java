package it.polimi.db2.telcoservice_sc42.ejb.entities;

import it.polimi.db2.telcoservice_sc42.ejb.primaryKeys.ValidityPrimaryKey;
import it.polimi.db2.telcoservice_sc42.utils.Representable;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Entity
@IdClass(ValidityPrimaryKey.class)
public class Validity implements Serializable, Representable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Id
    @ManyToOne
    @JoinColumn (name = "PACKAGE_ID")
    private ServicePackage servicePackage;

    private Integer period;

    @Column(name = "MONTHLY_FEE", precision = 2)
    private BigDecimal monthlyFee;

    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    @OneToMany(mappedBy="validity", fetch=FetchType.LAZY)
    private List<Order> orders;

    @Column(name = "TOTAL_COST")
    private BigDecimal totalCost;

    public Validity() {
    }

    public Validity(ServicePackage servicePackage, int period, BigDecimal monthlyFee, Date expirationDate ){
        this(servicePackage, period, monthlyFee);
        this.expirationDate = expirationDate;
    }

    public Validity(ServicePackage servicePackage, int period, BigDecimal monthlyFee ){
        this.setServicePackage(servicePackage);
        this.period = period;
        this.monthlyFee = monthlyFee.setScale(2, RoundingMode.HALF_UP);
        this.totalCost = monthlyFee.multiply(BigDecimal.valueOf(period)).setScale(2, RoundingMode.HALF_UP);
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
    public BigDecimal getMonthlyFee() {
        return monthlyFee;
    }
    public void setMonthlyFee(BigDecimal monthlyFee) {
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

    public void addOrder(Order order){
        getOrders().add(order);
    }

    @Override
    public String clientString() {
        String s = period == 1 ? "" : "s";
        return monthlyFee + " €/month for " + period + " month" + s;
    }
    
    @Override
    public String employeeString() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "Validity{" +
                "id=" + id +
                ", servicePackage=" + servicePackage.getName() +
                ", period=" + period +
                ", monthlyFee=" + monthlyFee +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
