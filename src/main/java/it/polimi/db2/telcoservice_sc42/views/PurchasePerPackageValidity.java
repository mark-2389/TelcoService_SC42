package it.polimi.db2.telcoservice_sc42.views;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "purchase_per_package_validity")
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
