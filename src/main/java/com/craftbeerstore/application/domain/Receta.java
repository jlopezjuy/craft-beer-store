package com.craftbeerstore.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Receta.
 */
@Entity
@Table(name = "receta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Receta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "brew_master")
    private String brewMaster;

    @Column(name = "batch", precision = 21, scale = 2)
    private BigDecimal batch;

    @Column(name = "temperatura_de_macerado", precision = 21, scale = 2)
    private BigDecimal temperaturaDeMacerado;

    @Column(name = "og", precision = 21, scale = 2)
    private BigDecimal og;

    @Column(name = "fg", precision = 21, scale = 2)
    private BigDecimal fg;

    @Column(name = "abv", precision = 21, scale = 2)
    private BigDecimal abv;

    @Column(name = "ibu", precision = 21, scale = 2)
    private BigDecimal ibu;

    @Column(name = "srm", precision = 21, scale = 2)
    private BigDecimal srm;

    @Column(name = "empaste", precision = 21, scale = 2)
    private BigDecimal empaste;

    @Column(name = "fecha")
    private LocalDate fecha;

    @ManyToOne
    @JsonIgnoreProperties("recetas")
    private Producto producto;

    @ManyToOne
    @JsonIgnoreProperties("recetas")
    private Insumo maltas;

    @ManyToOne
    @JsonIgnoreProperties("recetas")
    private Insumo lupulo;

    @ManyToOne
    @JsonIgnoreProperties("recetas")
    private Insumo levadura;

    @ManyToOne
    @JsonIgnoreProperties("recetas")
    private Insumo otros;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Receta nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBrewMaster() {
        return brewMaster;
    }

    public Receta brewMaster(String brewMaster) {
        this.brewMaster = brewMaster;
        return this;
    }

    public void setBrewMaster(String brewMaster) {
        this.brewMaster = brewMaster;
    }

    public BigDecimal getBatch() {
        return batch;
    }

    public Receta batch(BigDecimal batch) {
        this.batch = batch;
        return this;
    }

    public void setBatch(BigDecimal batch) {
        this.batch = batch;
    }

    public BigDecimal getTemperaturaDeMacerado() {
        return temperaturaDeMacerado;
    }

    public Receta temperaturaDeMacerado(BigDecimal temperaturaDeMacerado) {
        this.temperaturaDeMacerado = temperaturaDeMacerado;
        return this;
    }

    public void setTemperaturaDeMacerado(BigDecimal temperaturaDeMacerado) {
        this.temperaturaDeMacerado = temperaturaDeMacerado;
    }

    public BigDecimal getOg() {
        return og;
    }

    public Receta og(BigDecimal og) {
        this.og = og;
        return this;
    }

    public void setOg(BigDecimal og) {
        this.og = og;
    }

    public BigDecimal getFg() {
        return fg;
    }

    public Receta fg(BigDecimal fg) {
        this.fg = fg;
        return this;
    }

    public void setFg(BigDecimal fg) {
        this.fg = fg;
    }

    public BigDecimal getAbv() {
        return abv;
    }

    public Receta abv(BigDecimal abv) {
        this.abv = abv;
        return this;
    }

    public void setAbv(BigDecimal abv) {
        this.abv = abv;
    }

    public BigDecimal getIbu() {
        return ibu;
    }

    public Receta ibu(BigDecimal ibu) {
        this.ibu = ibu;
        return this;
    }

    public void setIbu(BigDecimal ibu) {
        this.ibu = ibu;
    }

    public BigDecimal getSrm() {
        return srm;
    }

    public Receta srm(BigDecimal srm) {
        this.srm = srm;
        return this;
    }

    public void setSrm(BigDecimal srm) {
        this.srm = srm;
    }

    public BigDecimal getEmpaste() {
        return empaste;
    }

    public Receta empaste(BigDecimal empaste) {
        this.empaste = empaste;
        return this;
    }

    public void setEmpaste(BigDecimal empaste) {
        this.empaste = empaste;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Receta fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Producto getProducto() {
        return producto;
    }

    public Receta producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Insumo getMaltas() {
        return maltas;
    }

    public Receta maltas(Insumo insumo) {
        this.maltas = insumo;
        return this;
    }

    public void setMaltas(Insumo insumo) {
        this.maltas = insumo;
    }

    public Insumo getLupulo() {
        return lupulo;
    }

    public Receta lupulo(Insumo insumo) {
        this.lupulo = insumo;
        return this;
    }

    public void setLupulo(Insumo insumo) {
        this.lupulo = insumo;
    }

    public Insumo getLevadura() {
        return levadura;
    }

    public Receta levadura(Insumo insumo) {
        this.levadura = insumo;
        return this;
    }

    public void setLevadura(Insumo insumo) {
        this.levadura = insumo;
    }

    public Insumo getOtros() {
        return otros;
    }

    public Receta otros(Insumo insumo) {
        this.otros = insumo;
        return this;
    }

    public void setOtros(Insumo insumo) {
        this.otros = insumo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Receta)) {
            return false;
        }
        return id != null && id.equals(((Receta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Receta{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", brewMaster='" + getBrewMaster() + "'" +
            ", batch=" + getBatch() +
            ", temperaturaDeMacerado=" + getTemperaturaDeMacerado() +
            ", og=" + getOg() +
            ", fg=" + getFg() +
            ", abv=" + getAbv() +
            ", ibu=" + getIbu() +
            ", srm=" + getSrm() +
            ", empaste=" + getEmpaste() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
