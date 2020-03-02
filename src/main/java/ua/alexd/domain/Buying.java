package ua.alexd.domain;

import javax.persistence.*;

@Entity
@Table(name = "Buyings")
public class Buying implements ShopDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "totalPrice")
    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "laptopId")
    private Laptop laptop;

    @ManyToOne
    @JoinColumn(name = "basketId")
    private Basket basket;

    public Buying() {
    }

    public Buying(int totalPrice, Laptop laptop, Basket basket) {
        this.totalPrice = totalPrice;
        this.laptop = laptop;
        this.basket = basket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }
}
