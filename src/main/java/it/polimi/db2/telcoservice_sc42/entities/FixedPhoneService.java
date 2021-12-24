package it.polimi.db2.telcoservice_sc42.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "fixed_phone_service")
public class FixedPhoneService extends Service {
    private static final long serialVersionUID = 1L;

    private Integer minutes;

    private Integer sms;

    @Column(name = "minutes_fee", precision = 2)
    private BigDecimal minutesFee;

    @Column(name = "sms_fee", precision = 2)
    private BigDecimal smsFee;

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