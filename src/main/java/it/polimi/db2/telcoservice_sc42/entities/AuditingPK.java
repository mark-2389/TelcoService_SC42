package it.polimi.db2.telcoservice_sc42.entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class AuditingPK implements Serializable {

    @SuppressWarnings({"unused"})
    private String username;

    @SuppressWarnings({"unused"})
    private Date rejectionDate;

    @SuppressWarnings({"unused"})
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
