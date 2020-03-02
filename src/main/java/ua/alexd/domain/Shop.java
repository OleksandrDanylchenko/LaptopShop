package ua.alexd.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Shops")
public class Shop implements ShopDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "shop")
    private List<Employee> shopEmployees;

    public Shop() {
    }

    public Shop(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Employee> getShopEmployees() {
        return shopEmployees;
    }

    public void setShopEmployees(List<Employee> shopEmployees) {
        this.shopEmployees = shopEmployees;
    }
}