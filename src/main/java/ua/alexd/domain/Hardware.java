package ua.alexd.domain;

import javax.persistence.*;

@Entity
@Table(name = "Hardware")
public class Hardware {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "asssemblyName")
    private String assemblyName;

    @ManyToOne
    @JoinColumn(name = "displayId")
    private Display display;

    @ManyToOne
    @JoinColumn(name = "cpuId")
    private CPU cpu;

    @ManyToOne
    @JoinColumn(name = "gpuId")
    private GPU gpu;

    @ManyToOne
    @JoinColumn(name = "ramId")
    private RAM ram;

    @ManyToOne
    @JoinColumn(name = "ssdId")
    private SSD ssd;

    @ManyToOne
    @JoinColumn(name = "hddId")
    private HDD hdd;

    public Hardware() {
    }

    public Hardware(String assemblyName, CPU cpu, GPU gpu, RAM ram, SSD ssd, HDD hdd, Display display) {
        this.assemblyName = assemblyName;
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.ssd = ssd;
        this.hdd = hdd;
        this.display = display;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssemblyName() {
        return assemblyName;
    }

    public void setAssemblyName(String assemblyName) {
        this.assemblyName = assemblyName;
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public GPU getGpu() {
        return gpu;
    }

    public void setGpu(GPU gpu) {
        this.gpu = gpu;
    }

    public RAM getRam() {
        return ram;
    }

    public void setRam(RAM ram) {
        this.ram = ram;
    }

    public SSD getSsd() {
        return ssd;
    }

    public void setSsd(SSD ssd) {
        this.ssd = ssd;
    }

    public HDD getHdd() {
        return hdd;
    }

    public void setHdd(HDD hdd) {
        this.hdd = hdd;
    }
}