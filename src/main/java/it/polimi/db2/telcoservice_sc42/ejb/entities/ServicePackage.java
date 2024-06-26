package it.polimi.db2.telcoservice_sc42.ejb.entities;

import it.polimi.db2.telcoservice_sc42.utils.Representable;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "service_package")
@NamedQuery(name = "ServicePackage.valid", query = "SELECT p FROM ServicePackage p WHERE ( p.expirationDate >= current_date OR p.expirationDate = null ) ")
public class ServicePackage implements Serializable, Representable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    @OneToMany(mappedBy = "servicePackage", fetch=FetchType.LAZY)
    private List<Order> orders;

    @ManyToMany ( fetch = FetchType.EAGER, cascade = CascadeType.PERSIST )
    @JoinTable(
            name = "service_composition",
            joinColumns = @JoinColumn(name="PACKAGE_ID"),
            inverseJoinColumns = @JoinColumn(name="SERVICE_ID"))
    private List<Service> services;

    @ManyToMany ( fetch = FetchType.EAGER, cascade = CascadeType.PERSIST )
    @JoinTable
            (name = "optional_product_composition",
                    joinColumns = @JoinColumn(name="package_id"),
                    inverseJoinColumns = @JoinColumn(name="optional_product_id"))
    private List<OptionalProduct> products;

    @OneToMany(mappedBy = "servicePackage", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Validity> validities;

    public ServicePackage(){

    }

    public ServicePackage(String name) {
        this.name = name;
        this.expirationDate = null;
    }

    public ServicePackage(String name, Date expirationDate) {
        this.name = name;
        this.expirationDate = expirationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<Validity> getValidities() {
        return validities;
    }

    public void setValidities(List<Validity> validities) {
        this.validities = validities;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Integer> getServiceIds() {
        if ( services == null ) return new ArrayList<>();
        return services.stream().map(Service::getId).collect(Collectors.toList());
    }

    public List<OptionalProduct> getProducts() {
        return products;
    }

    public void setOptionalProducts(List<OptionalProduct> products) {
        this.products = products;
    }

    public void addOrder(Order order){
        getOrders().add(order);
    }

    public void addValidity(Validity validity) {
        getValidities().add(validity);
    }

    public void removeValidity(Validity validity) {
        getValidities().remove(validity);
    }

    public void addService(Service newService) {
        getServices().add(newService);
        newService.addPackage(this);
    }

    public void removeService(Service oldService) {
        getServices().remove(oldService);
        oldService.removePackage(this);
    }

    public void addOptionalProduct(OptionalProduct newOptional) {
        getProducts().add(newOptional);
        newOptional.addPackage(this);
    }

    public void removeOptionalProduct(OptionalProduct oldOptional) {
        getProducts().remove(oldOptional);
        oldOptional.removePackage(this);
    }

    @Override
    public String clientString() {
        return id + " - " + name;
    }

    @Override
    public String employeeString() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "ServicePackage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", expirationDate=" + expirationDate +
                ", services=" + services +
                ", products=" + products +
                ", validities=" + validities +
                '}';
    }
}
