package it.polimi.db2.telcoservice_sc42.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;

@Entity
@Table(name = "mobile_phone_service")
@DiscriminatorValue("MOBILE_PHONE")
public class MobilePhoneService extends Service {
    private static final long serialVersionUID = 1L;

    private Integer minutes;

    private Integer sms;

    @Column(name = "minutes_fee", precision = 2)
    private BigDecimal minutesFee;

    @Column(name = "sms_fee", precision = 2)
    private BigDecimal smsFee;

    public MobilePhoneService() {
        // by default the empty constructor should set numeric values to 0 when not specified (please check)
    }

    public String clientString() {
        return super.clientString() + " - " +
                minutes + " minutes ( " + minutesFee + " € ) - " +
                sms + " sms ( " + smsFee + " € )";
    }

    public String employeeString() {
        return this.toString();
    }

    public String toString() {
        String str = super.toString();
        return str + " - " + minutes + " minutes ( " + minutesFee + " € ) - " + sms + " sms ( " + smsFee + " € )";
    }

    public MobilePhoneService(Date expirationDate, Integer minutes, BigDecimal minutesFee, Integer sms, BigDecimal smsFee) {
        super(ServiceType.MOBILE_PHONE, expirationDate);
        this.minutes = minutes;
        this.minutesFee = minutesFee.setScale(2, RoundingMode.HALF_UP);
        this.sms = sms;
        this.smsFee = smsFee.setScale(2, RoundingMode.HALF_UP);
    }


    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getSms() {
        return sms;
    }

    public void setSms(Integer sms) {
        this.sms = sms;
    }

    public BigDecimal getMinutesFee() {
        return minutesFee;
    }

    public void setMinutesFee(BigDecimal minutesFee) {
        this.minutesFee = minutesFee;
    }

    public BigDecimal getSmsFee() {
        return smsFee;
    }

    public void setSmsFee(BigDecimal smsFee) {
        this.smsFee = smsFee;
    }
}