package ua.alexd.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Clients")
public class Client implements ShopDomain {
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
    private Date dateReg;

    public Client() {
    }

    public Client(String firstName, String secondName, Date dateReg) {
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

    public Date getDateReg() {
        return dateReg;
    }

    public void setDateReg(Date dateReg) {
        this.dateReg = dateReg;
    }
}