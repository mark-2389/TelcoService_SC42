package it.polimi.db2.telcoservice_sc42.entities;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;

@Entity
@Table(name = "mobile_internet_service")
@DiscriminatorValue("MOBILE_INTERNET")
public class MobileInternetService extends Service {
    private static final long serialVersionUID = 1L;

    @Column(name = "gb")
    private Integer gigaByte;

    @Column(name = "gb_fee", precision = 2)
    private BigDecimal gigaByteFee;

    public MobileInternetService() {

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

    public MobileInternetService(Date expirationDate, Integer gb, BigDecimal gbFee) {
        super(ServiceType.MOBILE_INTERNET, expirationDate);
        this.gigaByte = gb;
        this.gigaByteFee = gbFee.setScale(2, RoundingMode.HALF_UP);
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