package it.polimi.db2.telcoservice_sc42.views;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "purchase_per_package")
@NamedQuery(name = "PurchasePerPackage.all", query = "SELECT p FROM PurchasePerPackage p")
public class PurchasePerPackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "package_id")
    private Integer packageId;

    private Integer purchases;

    public Integer getPackageId() {
        return packageId;
    }

    public Integer getPurchases() {
        return purchases;
    }
}