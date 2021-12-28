package it.polimi.db2.telcoservice_sc42.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "purchase_per_package")
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