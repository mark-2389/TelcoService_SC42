package it.polimi.db2.telcoservice_sc42.ejb.views;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "value_per_package_op")
@NamedQuery(name = "ValuePerPackageWithOptionalProduct.all", query = "SELECT v FROM ValuePerPackageWithOptionalProduct v")
@NamedQuery(name = "ValuePerPackageWithOptionalProduct.named", query = "SELECT v.packageId, s.name, v.total FROM ValuePerPackageWithOptionalProduct v JOIN ServicePackage s ON v.packageId = s.id")
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
