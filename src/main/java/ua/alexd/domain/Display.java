package ua.alexd.domain;

import javax.persistence.*;

@Entity
@Table(name = "Displays")
public class Display implements ShopDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "model")
    private String model;

    @Basic
    @Column(name = "type")
    private String type;

    @Basic
    @Column(name = "diagonal")
    private String diagonal;

    @Basic
    @Column(name = "resolution")
    private String resolution;

    public Display() {
    }

    public Display(String model, String type, String diagonal, String resolution) {
        this.model = model;
        this.type = type;
        this.diagonal = diagonal;
        this.resolution = resolution;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(String diagonal) {
        this.diagonal = diagonal;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}