package it.polimi.db2.telcoservice_sc42.ejb.entities;

import it.polimi.db2.telcoservice_sc42.ejb.enums.ServiceType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "fixed_internet_service")
@DiscriminatorValue("FIXED_INTERNET")
public class FixedInternetService extends Service {
    private static final long serialVersionUID = 1L;

    @Column(name = "gb")
    private Integer gigaByte;

    @Column(name = "gb_fee", precision = 2)
    private BigDecimal gigaByteFee;

    public FixedInternetService() {

    }

    public String clientString() {
        return super.clientString() + " - " + gigaByte + " gb ( " + gigaByteFee + " € )";
    }

    public String employeeString() {
        return this.toString();
    }

    public String toString() {
        String str = super.toString();
        return str + " - " + gigaByte + " gb ( " + gigaByteFee + " € )";
    }

    public FixedInternetService(Date expirationDate, Integer gb, BigDecimal gbFee) {
        super(ServiceType.FIXED_INTERNET, expirationDate);
        this.gigaByte = gb;
        this.gigaByteFee = gbFee;
    }

    public Integer getGigaByte() {
        return gigaByte;
    }

    public void setGigaByte(Integer gigaByte) {
        this.gigaByte = gigaByte;
    }

    public BigDecimal getGigaByteFee() {
        return gigaByteFee;
    }

    public void setGigaByteFee(BigDecimal gigaByteFee) {
        this.gigaByteFee = gigaByteFee;
    }

}
