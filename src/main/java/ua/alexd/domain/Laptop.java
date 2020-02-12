package ua.alexd.domain;

import javax.persistence.*;

@Entity
@Table(name = "Laptops")
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "producerId")
    private Producer producer;

    @ManyToOne
    @JoinColumn(name = "typeId")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "displayId")
    private Display display;

    @ManyToOne
    @JoinColumn(name = "hardwareId")
    private Hardware hardware;

    @ManyToOne
    @JoinColumn(name = "weightId")
    private Weight weight;

    public Laptop() {
    }

    public Laptop(Producer producer, Type type, Hardware hardware, Weight weight) {
        this.producer = producer;
        this.type = type;
        this.hardware = hardware;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }
}