package com.craftbeerstore.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.craftbeerstore.application.domain.enumeration.TipoPresentacion;

/**
 * A Presentacion.
 */
@Entity
@Table(name = "presentacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Presentacion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_presentacion", nullable = false)
    private TipoPresentacion tipoPresentacion;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Long cantidad;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "costo_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal costoUnitario;

    @NotNull
    @Column(name = "precio_venta_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioVentaUnitario;

    @NotNull
    @Column(name = "precio_venta_total", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioVentaTotal;

    @NotNull
    @Column(name = "precio_costo_total", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioCostoTotal;

    @ManyToOne
    @JsonIgnoreProperties("presentacions")
    private Producto producto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPresentacion getTipoPresentacion() {
        return tipoPresentacion;
    }

    public Presentacion tipoPresentacion(TipoPresentacion tipoPresentacion) {
        this.tipoPresentacion = tipoPresentacion;
        return this;
    }

    public void setTipoPresentacion(TipoPresentacion tipoPresentacion) {
        this.tipoPresentacion = tipoPresentacion;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public Presentacion cantidad(Long cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Presentacion fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public Presentacion costoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
        return this;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getPrecioVentaUnitario() {
        return precioVentaUnitario;
    }

    public Presentacion precioVentaUnitario(BigDecimal precioVentaUnitario) {
        this.precioVentaUnitario = precioVentaUnitario;
        return this;
    }

    public void setPrecioVentaUnitario(BigDecimal precioVentaUnitario) {
        this.precioVentaUnitario = precioVentaUnitario;
    }

    public BigDecimal getPrecioVentaTotal() {
        return precioVentaTotal;
    }

    public Presentacion precioVentaTotal(BigDecimal precioVentaTotal) {
        this.precioVentaTotal = precioVentaTotal;
        return this;
    }

    public void setPrecioVentaTotal(BigDecimal precioVentaTotal) {
        this.precioVentaTotal = precioVentaTotal;
    }

    public BigDecimal getPrecioCostoTotal() {
        return precioCostoTotal;
    }

    public Presentacion precioCostoTotal(BigDecimal precioCostoTotal) {
        this.precioCostoTotal = precioCostoTotal;
        return this;
    }

    public void setPrecioCostoTotal(BigDecimal precioCostoTotal) {
        this.precioCostoTotal = precioCostoTotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public Presentacion producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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
        Presentacion presentacion = (Presentacion) o;
        if (presentacion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), presentacion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Presentacion{" +
            "id=" + getId() +
            ", tipoPresentacion='" + getTipoPresentacion() + "'" +
            ", cantidad=" + getCantidad() +
            ", fecha='" + getFecha() + "'" +
            ", costoUnitario=" + getCostoUnitario() +
            ", precioVentaUnitario=" + getPrecioVentaUnitario() +
            ", precioVentaTotal=" + getPrecioVentaTotal() +
            ", precioCostoTotal=" + getPrecioCostoTotal() +
            "}";
    }
}
