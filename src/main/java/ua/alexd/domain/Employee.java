package ua.alexd.domain;

import javax.persistence.*;

@Entity
@Table(name = "Employees")
public class Employee {
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
    @Column(name = "isWorking")
    private Boolean isWorking;

    @ManyToOne
    @JoinColumn(name = "shopId")
    private Shop shop;

    public Employee() {
    }

    public Employee(String firstName, String secondName, Shop shop, boolean isWorking) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.shop = shop;
        this.isWorking = isWorking;
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Boolean getIsWorking() {
        return isWorking;
    }

    public void setActive(Boolean active) {
        isWorking = active;
    }
}