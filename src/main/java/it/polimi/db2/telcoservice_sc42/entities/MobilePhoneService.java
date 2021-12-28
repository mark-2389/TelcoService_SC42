package it.polimi.db2.telcoservice_sc42.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.math.BigDecimal;

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

    public MobilePhoneService(Date expirationDate, Integer minutes, BigDecimal minutesFee, Integer sms, BigDecimal smsFee) {
        super(ServiceType.MOBILE_PHONE, expirationDate);
        this.minutes = minutes;
        this.minutesFee = minutesFee;
        this.sms = sms;
        this.smsFee = smsFee;
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