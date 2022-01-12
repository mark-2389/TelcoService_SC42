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

    private String email;

    @Column(name = "AMOUNT", nullable = false, precision = 2)
    private BigDecimal amount;

    @Id
    @Column(name = "REJECTION_DATE", nullable = false)
    private Date rejectionDate;

    @Id
    @Column(name = "REJECTION_TIME", nullable = false)
    private Time rejectionTime;

    @Column(name = "IS_ACTIVE", nullable = true)
    private Boolean isActive;

    public String getUsername() {
        return username.getUsername();
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getRejectionDate() {
        return rejectionDate;
    }

    public Time getRejectionTime() {
        return rejectionTime;
    }

    public Boolean getIsActive() {
        return isActive;
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
