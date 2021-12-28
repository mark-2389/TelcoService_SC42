package it.polimi.db2.telcoservice_sc42.views;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "value_per_package_op")
public class ValuePerPackageWithOptionalProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "package_id")
    private Integer packageId;

    private BigInteger total;

    public Integer getPackageId() {
        return packageId;
    }

    public BigInteger getTotal() {
        return total;
    }
}
