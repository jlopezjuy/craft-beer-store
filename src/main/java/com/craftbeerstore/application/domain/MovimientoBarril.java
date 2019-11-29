package com.craftbeerstore.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.craftbeerstore.application.domain.enumeration.EstadoMovimientoBarril;

/**
 * A MovimientoBarril.
 */
@Entity
@Table(name = "movimiento_barril")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MovimientoBarril implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_movimiento")
    private LocalDate fechaMovimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoMovimientoBarril estado;

    @Column(name = "dias")
    private Long dias;

    @ManyToOne
    @JsonIgnoreProperties("movimientoBarrils")
    private Barril barril;

    @ManyToOne
    @JsonIgnoreProperties("movimientoBarrils")
    private Producto producto;

    @ManyToOne
    @JsonIgnoreProperties("movimientoBarrils")
    private Lote lote;

    @ManyToOne
    @JsonIgnoreProperties("movimientoBarrils")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaMovimiento() {
        return fechaMovimiento;
    }

    public MovimientoBarril fechaMovimiento(LocalDate fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
        return this;
    }

    public void setFechaMovimiento(LocalDate fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public EstadoMovimientoBarril getEstado() {
        return estado;
    }

    public MovimientoBarril estado(EstadoMovimientoBarril estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoMovimientoBarril estado) {
        this.estado = estado;
    }

    public Long getDias() {
        return dias;
    }

    public MovimientoBarril dias(Long dias) {
        this.dias = dias;
        return this;
    }

    public void setDias(Long dias) {
        this.dias = dias;
    }

    public Barril getBarril() {
        return barril;
    }

    public MovimientoBarril barril(Barril barril) {
        this.barril = barril;
        return this;
    }

    public void setBarril(Barril barril) {
        this.barril = barril;
    }

    public Producto getProducto() {
        return producto;
    }

    public MovimientoBarril producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Lote getLote() {
        return lote;
    }

    public MovimientoBarril lote(Lote lote) {
        this.lote = lote;
        return this;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public MovimientoBarril cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovimientoBarril)) {
            return false;
        }
        return id != null && id.equals(((MovimientoBarril) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MovimientoBarril{" +
            "id=" + getId() +
            ", fechaMovimiento='" + getFechaMovimiento() + "'" +
            ", estado='" + getEstado() + "'" +
            ", dias=" + getDias() +
            "}";
    }
}
