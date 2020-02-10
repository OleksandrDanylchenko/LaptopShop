package ua.alexd.domain;

import javax.persistence.*;

@Entity
@Table(name = "RAMs")
public class RAM {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "model")
    private String model;

    @Basic
    @Column(name = "memory")
    private int memory;

    public RAM() {
    }

    public RAM(String model, int memory) {
        this.model = model;
        this.memory = memory;
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

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }
}