package it.polimi.db2.telcoservice_sc42.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Entity
@IdClass(AuditingPK.class)
@NamedQuery(name = "Auditing.allActive", query = "SELECT a FROM Auditing a WHERE a.isActive = true")
public class Auditing implements Serializable {
    private static final long serialVersionUID = 1L;
    //todo check for JPA annotations

    @Id
    @ManyToOne
    @JoinColumn (name = "username")
    private Client username;

    @ManyToOne
    @JoinColumn (name = "email")
    private Client email;

    @Basic
    @Column(name = "AMOUNT", nullable = false, precision = 2)
    private BigDecimal amount;

    @Id
    @Column(name = "REJECTION_DATE", nullable = false)
    private Date rejectionDate;

    @Id
    @Column(name = "REJECTION_TIME", nullable = false)
    private Time rejectionTime;

    @Basic
    @Column(name = "IS_ACTIVE", nullable = true)
    private Boolean isActive;

    public Client getUsername() {
        return username;
    }

    public void setUsername(Client username) {
        this.username = username;
    }

    public Client getEmail() {
        return email;
    }

    public void setEmail(Client email) {
        this.email = email;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(Date rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public Time getRejectionTime() {
        return rejectionTime;
    }

    public void setRejectionTime(Time rejectionTime) {
        this.rejectionTime = rejectionTime;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Auditing{" +
                "username=" + username +
                ", email=" + email +
                ", amount=" + amount +
                ", rejectionDate=" + rejectionDate +
                ", rejectionTime=" + rejectionTime +
                '}';
    }
}
