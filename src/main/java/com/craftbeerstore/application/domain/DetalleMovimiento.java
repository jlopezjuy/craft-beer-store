package com.craftbeerstore.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DetalleMovimiento.
 */
@Entity
@Table(name = "detalle_movimiento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DetalleMovimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Long cantidad;

    @NotNull
    @Column(name = "precio_total", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioTotal;

    @ManyToOne
    private Presentacion presentacion;

    @ManyToOne
    private Movimientos movimientos;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public DetalleMovimiento cantidad(Long cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public DetalleMovimiento precioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
        return this;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Presentacion getPresentacion() {
        return presentacion;
    }

    public DetalleMovimiento presentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
        return this;
    }

    public void setPresentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
    }

    public Movimientos getMovimientos() {
        return movimientos;
    }

    public DetalleMovimiento movimientos(Movimientos movimientos) {
        this.movimientos = movimientos;
        return this;
    }

    public void setMovimientos(Movimientos movimientos) {
        this.movimientos = movimientos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DetalleMovimiento detalleMovimiento = (DetalleMovimiento) o;
        if (detalleMovimiento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detalleMovimiento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetalleMovimiento{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", precioTotal=" + getPrecioTotal() +
            "}";
    }
}
