package ua.alexd.domain;

import javax.persistence.*;

@Entity
@Table(name = "Weights")
public class Weight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "mass")
    private float mass;

    public Weight() {
    }

    public Weight(float mass) {
        this.mass = mass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }
}