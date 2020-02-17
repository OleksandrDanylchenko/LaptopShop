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
    private Label label;

    @ManyToOne
    @JoinColumn(name = "typeId")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "hardwareId")
    private Hardware hardware;

    public Laptop() {
    }

    public Laptop(Label label, Type type, Hardware hardware) {
        this.label = label;
        this.type = type;
        this.hardware = hardware;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
    }
}