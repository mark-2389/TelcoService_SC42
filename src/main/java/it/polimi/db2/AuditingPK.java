package it.polimi.db2;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class AuditingPK implements Serializable {

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private String username;

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private Date rejectionDate;

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private Time rejectionTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuditingPK that = (AuditingPK) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (rejectionDate != null ? !rejectionDate.equals(that.rejectionDate) : that.rejectionDate != null)
            return false;
        if (rejectionTime != null ? !rejectionTime.equals(that.rejectionTime) : that.rejectionTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (rejectionDate != null ? rejectionDate.hashCode() : 0);
        result = 31 * result + (rejectionTime != null ? rejectionTime.hashCode() : 0);
        return result;
    }
}
