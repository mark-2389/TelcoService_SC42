package it.polimi.db2.telcoservice_sc42.ejb.entities;

import it.polimi.db2.telcoservice_sc42.ejb.enums.ServiceType;
import it.polimi.db2.telcoservice_sc42.utils.Representable;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "service")
@Inheritance(
        strategy = InheritanceType.JOINED
)
@DiscriminatorColumn(
        name="TYPE",
        discriminatorType = DiscriminatorType.STRING
)
@DiscriminatorValue("FIXED_PHONE")
@NamedQuery(name = "Service.valid", query = "SELECT s FROM Service s WHERE ( s.expirationDate = NULL OR s.expirationDate >= current_date )")
public class Service implements Serializable, Representable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "ENUM('FIXED_PHONE', 'MOBILE_PHONE', 'FIXED_INTERNET', 'MOBILE_INTERNET')")
    @Enumerated(EnumType.STRING)
    private ServiceType type;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @ManyToMany (mappedBy = "services", fetch = FetchType.LAZY )
    private List<ServicePackage> packages;

    @OneToMany (mappedBy = "service", fetch = FetchType.LAZY )
    private List<OrderServiceSchedule> serviceSchedules;

    public Service() {

    }

    public Service(ServiceType type, Date expirationDate) {
        this.type = type;
        this.expirationDate = expirationDate;
    }

    public String clientString() {
        return this.type.description();
    }

    public String employeeString() {
        return toString();
    }

    public String toString() {
        return this.type.description() + " - " + this.expirationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public List<OrderServiceSchedule> getServiceSchedules() {
        return serviceSchedules;
    }

    public List<ServicePackage> getPackages() {
        return packages;
    }

    public void setPackages(List<ServicePackage> packages) {
        this.packages = packages;
    }


    public void addPackage(ServicePackage servicePackage) {
        getPackages().add(servicePackage);
    }

    public void removePackage(ServicePackage servicePackage) {
        getPackages().remove(servicePackage);
    }
}
