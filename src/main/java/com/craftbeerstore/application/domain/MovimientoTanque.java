package com.craftbeerstore.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.craftbeerstore.application.domain.enumeration.EstadoUsoTanque;

/**
 * A MovimientoTanque.
 */
@Entity
@Table(name = "movimiento_tanque")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MovimientoTanque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoUsoTanque estado;

    @Column(name = "dias")
    private Integer dias;

    @ManyToOne
    @JsonIgnoreProperties("movimientoTanques")
    private Tanque tanque;

    @ManyToOne
    @JsonIgnoreProperties("movimientoTanques")
    private Producto producto;

    @ManyToOne
    @JsonIgnoreProperties("movimientoTanques")
    private Lote lote;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public MovimientoTanque fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EstadoUsoTanque getEstado() {
        return estado;
    }

    public MovimientoTanque estado(EstadoUsoTanque estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoUsoTanque estado) {
        this.estado = estado;
    }

    public Integer getDias() {
        return dias;
    }

    public MovimientoTanque dias(Integer dias) {
        this.dias = dias;
        return this;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Tanque getTanque() {
        return tanque;
    }

    public MovimientoTanque tanque(Tanque tanque) {
        this.tanque = tanque;
        return this;
    }

    public void setTanque(Tanque tanque) {
        this.tanque = tanque;
    }

    public Producto getProducto() {
        return producto;
    }

    public MovimientoTanque producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Lote getLote() {
        return lote;
    }

    public MovimientoTanque lote(Lote lote) {
        this.lote = lote;
        return this;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovimientoTanque)) {
            return false;
        }
        return id != null && id.equals(((MovimientoTanque) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MovimientoTanque{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", estado='" + getEstado() + "'" +
            ", dias=" + getDias() +
            "}";
    }
}
