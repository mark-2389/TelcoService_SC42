package it.polimi.db2.telcoservice_sc42.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.persistence.Enumerated;
import java.util.List;

@Entity
@NamedQuery(name = "Client.withCredentials", query = "SELECT r FROM Client r  WHERE r.username = ?1 and r.password = ?2")
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    //TODO: consider adding an id.
    private String username;
    private String password;
    private String email;

    @Column(name = "number_rejection")
    private Integer numberOfRejections;

    @Column(columnDefinition = "ENUM('solvent', 'insolvent')")
    @Enumerated(EnumType.STRING)
    private UserStatus insolvent;

    // A client can do many orders.
    @OneToMany(mappedBy="client", fetch=FetchType.LAZY)
    private List<Order> orders;

    public Client() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return email;
    }

    public void setMail(String email) {
        this.email = email;
    }

    public int getNumberOfRejections() {
        return numberOfRejections;
    }

    public void setNumberOfRejections(int numberOfRejections) {
        this.numberOfRejections = numberOfRejections;
    }
    public UserStatus getInsolvent() {
        return insolvent;
    }

    public void setInsolvent(UserStatus insolvent) {
        this.insolvent = insolvent;
    }

}
