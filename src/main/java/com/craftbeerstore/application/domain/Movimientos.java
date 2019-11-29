package com.craftbeerstore.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.craftbeerstore.application.domain.enumeration.TipoMovimiento;

import com.craftbeerstore.application.domain.enumeration.EstadoMovimiento;

/**
 * A Movimientos.
 */
@Entity
@Table(name = "movimientos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Movimientos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false)
    private TipoMovimiento tipoMovimiento;

    @NotNull
    @Column(name = "fecha_movimiento", nullable = false)
    private LocalDate fechaMovimiento;

    @NotNull
    @Column(name = "precio_total", precision = 21, scale = 2, nullable = false)
    private BigDecimal precioTotal;

    @NotNull
    @Column(name = "numero_movimiento", nullable = false)
    private String numeroMovimiento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoMovimiento estado;

    @Column(name = "litros_totales", precision = 21, scale = 2)
    private BigDecimal litrosTotales;

    @ManyToOne
    @JsonIgnoreProperties("movimientos")
    private Cliente cliente;

    @ManyToOne
    @JsonIgnoreProperties("movimientos")
    private Empresa empresa;

    @ManyToOne
    @JsonIgnoreProperties("movimientos")
    private PuntoDeVenta puntoDeVenta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public Movimientos tipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
        return this;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public LocalDate getFechaMovimiento() {
        return fechaMovimiento;
    }

    public Movimientos fechaMovimiento(LocalDate fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
        return this;
    }

    public void setFechaMovimiento(LocalDate fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public Movimientos precioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
        return this;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getNumeroMovimiento() {
        return numeroMovimiento;
    }

    public Movimientos numeroMovimiento(String numeroMovimiento) {
        this.numeroMovimiento = numeroMovimiento;
        return this;
    }

    public void setNumeroMovimiento(String numeroMovimiento) {
        this.numeroMovimiento = numeroMovimiento;
    }

    public EstadoMovimiento getEstado() {
        return estado;
    }

    public Movimientos estado(EstadoMovimiento estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoMovimiento estado) {
        this.estado = estado;
    }

    public BigDecimal getLitrosTotales() {
        return litrosTotales;
    }

    public Movimientos litrosTotales(BigDecimal litrosTotales) {
        this.litrosTotales = litrosTotales;
        return this;
    }

    public void setLitrosTotales(BigDecimal litrosTotales) {
        this.litrosTotales = litrosTotales;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Movimientos cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Movimientos empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public PuntoDeVenta getPuntoDeVenta() {
        return puntoDeVenta;
    }

    public Movimientos puntoDeVenta(PuntoDeVenta puntoDeVenta) {
        this.puntoDeVenta = puntoDeVenta;
        return this;
    }

    public void setPuntoDeVenta(PuntoDeVenta puntoDeVenta) {
        this.puntoDeVenta = puntoDeVenta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movimientos)) {
            return false;
        }
        return id != null && id.equals(((Movimientos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Movimientos{" +
            "id=" + getId() +
            ", tipoMovimiento='" + getTipoMovimiento() + "'" +
            ", fechaMovimiento='" + getFechaMovimiento() + "'" +
            ", precioTotal=" + getPrecioTotal() +
            ", numeroMovimiento='" + getNumeroMovimiento() + "'" +
            ", estado='" + getEstado() + "'" +
            ", litrosTotales=" + getLitrosTotales() +
            "}";
    }
}
