package it.polimi.db2.telcoservice_sc42.entities;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;

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

    public MobileInternetService(Date expirationDate, Integer gb, BigDecimal gbFee) {
        super(ServiceType.MOBILE_INTERNET, expirationDate);
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