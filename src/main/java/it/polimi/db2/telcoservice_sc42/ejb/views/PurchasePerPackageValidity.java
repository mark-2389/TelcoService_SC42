package it.polimi.db2.telcoservice_sc42.ejb.views;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "purchase_per_package_validity")
@NamedQuery(name = "PurchasePerPackageValidity.all", query = "SELECT p FROM PurchasePerPackageValidity p")
@NamedQuery(name = "PurchasePerPackageValidity.named", query = "SELECT p.packageId, p.validityId, s.name, v.period, p.purchases FROM PurchasePerPackageValidity p JOIN ServicePackage s JOIN Validity v ON p.packageId = s.id AND p.validityId = v.id")
public class PurchasePerPackageValidity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PACKAGE_ID")
    private Integer packageId;

    @Id
    @Column(name = "VALIDITY_ID")
    private Integer validityId;

    private Integer purchases;

    public Integer getPackageId() {
        return packageId;
    }

    public Integer getValidityId() {
        return validityId;
    }

    public Integer getPurchases() {
        return purchases;
    }

}
