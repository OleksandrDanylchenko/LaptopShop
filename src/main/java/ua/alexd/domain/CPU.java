package ua.alexd.domain;

import javax.persistence.*;

@Entity
@Table(name = "CPUs")
public class CPU {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "model")
    private String model;

    @Basic
    @Column(name = "frequency")
    private float frequency;

    public CPU() {
    }

    public CPU(String model, float frequency) {
        this.model = model;
        this.frequency = frequency;
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

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }
}