package com.craftbeerstore.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.craftbeerstore.application.domain.enumeration.TipoTanque;

import com.craftbeerstore.application.domain.enumeration.EstadoTanque;

/**
 * A Tanque.
 */
@Entity
@Table(name = "tanque")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tanque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "litros", precision = 21, scale = 2)
    private BigDecimal litros;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoTanque tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoTanque estado;

    @Column(name = "listros_disponible", precision = 21, scale = 2)
    private BigDecimal listrosDisponible;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @ManyToOne
    @JsonIgnoreProperties("tanques")
    private Lote lote;

    @ManyToOne
    @JsonIgnoreProperties("tanques")
    private Producto producto;

    @ManyToOne
    @JsonIgnoreProperties("tanques")
    private Empresa empresa;

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

    public Tanque nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getLitros() {
        return litros;
    }

    public Tanque litros(BigDecimal litros) {
        this.litros = litros;
        return this;
    }

    public void setLitros(BigDecimal litros) {
        this.litros = litros;
    }

    public TipoTanque getTipo() {
        return tipo;
    }

    public Tanque tipo(TipoTanque tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoTanque tipo) {
        this.tipo = tipo;
    }

    public EstadoTanque getEstado() {
        return estado;
    }

    public Tanque estado(EstadoTanque estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoTanque estado) {
        this.estado = estado;
    }

    public BigDecimal getListrosDisponible() {
        return listrosDisponible;
    }

    public Tanque listrosDisponible(BigDecimal listrosDisponible) {
        this.listrosDisponible = listrosDisponible;
        return this;
    }

    public void setListrosDisponible(BigDecimal listrosDisponible) {
        this.listrosDisponible = listrosDisponible;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public Tanque fechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
        return this;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Lote getLote() {
        return lote;
    }

    public Tanque lote(Lote lote) {
        this.lote = lote;
        return this;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Producto getProducto() {
        return producto;
    }

    public Tanque producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Tanque empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tanque)) {
            return false;
        }
        return id != null && id.equals(((Tanque) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tanque{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", litros=" + getLitros() +
            ", tipo='" + getTipo() + "'" +
            ", estado='" + getEstado() + "'" +
            ", listrosDisponible=" + getListrosDisponible() +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            "}";
    }
}
