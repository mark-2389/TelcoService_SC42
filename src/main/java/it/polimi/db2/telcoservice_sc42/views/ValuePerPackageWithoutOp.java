package it.polimi.db2.telcoservice_sc42.views;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "value_per_package_without")
@NamedQuery(name = "ValuePerPackageWithoutOp.all", query = "SELECT v FROM ValuePerPackageWithoutOp v")
@NamedQuery(name = "ValuePerPackageWithoutOp.named", query = "SELECT v.packageId, s.name, v.total FROM ValuePerPackageWithoutOp v JOIN ServicePackage s  ON v.packageId = s.id")
public class ValuePerPackageWithoutOp implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "package_id")
    private Integer packageId;

    private Integer total;

    public Integer getPackageId() {
        return packageId;
    }

    public Integer getTotal() {
        return total;
    }
}
