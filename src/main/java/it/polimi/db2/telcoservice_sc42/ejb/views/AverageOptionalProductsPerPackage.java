package it.polimi.db2.telcoservice_sc42.ejb.views;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "average_opproducts_per_servpackage")
@NamedQuery(name = "AverageOptionalProductsPerPackage.all", query = "SELECT aop FROM AverageOptionalProductsPerPackage aop")
@NamedQuery(name = "AverageOptionalProductsPerPackage.named", query = "SELECT aop.packageId, s.name, aop.averageProducts FROM AverageOptionalProductsPerPackage aop JOIN ServicePackage s ON aop.packageId = s.id")
public class AverageOptionalProductsPerPackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "package_id")
    private Integer packageId;

    @Column(name = "average_products")
    private Float averageProducts;

    public Integer getPackageId() {
        return packageId;
    }

    public Float getAverageProducts() {
        return averageProducts;
    }
}
