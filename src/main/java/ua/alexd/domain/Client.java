package ua.alexd.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "firstName")
    private String firstName;

    @Basic
    @Column(name = "secondName")
    private String secondName;

    @Basic
    @Column(name = "dateReg")
    private Timestamp dateReg;

    public Client() {
    }

    public Client(String firstName, String secondName, Timestamp dateReg) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.dateReg = dateReg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Timestamp getDateReg() {
        return dateReg;
    }

    public void setDateReg(Timestamp dateReg) {
        this.dateReg = dateReg;
    }
}