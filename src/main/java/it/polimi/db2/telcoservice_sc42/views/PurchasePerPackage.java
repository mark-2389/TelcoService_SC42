package it.polimi.db2.telcoservice_sc42.views;

import it.polimi.db2.telcoservice_sc42.entities.ServicePackage;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Table(name = "purchase_per_package")
@NamedQuery(name = "PurchasePerPackage.all", query = "SELECT p FROM PurchasePerPackage p")
@NamedQuery(name = "PurchasePerPackage.named", query = "SELECT p.packageId, s.name, p.purchases FROM PurchasePerPackage p JOIN ServicePackage s ON p.packageId = s.id")
public class PurchasePerPackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "package_id")
    private Integer packageId;

    private Integer purchases;

    @Transient
    private String name;

    public Integer getPackageId() {
        return packageId;
    }

    public Integer getPurchases() {
        return purchases;
    }

    @Override
    public String toString() {
        return "packageId: " + packageId +
                ", purchases: " + purchases;
    }
}