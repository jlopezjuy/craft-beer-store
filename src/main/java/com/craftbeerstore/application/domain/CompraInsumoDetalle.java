package com.craftbeerstore.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.craftbeerstore.application.domain.enumeration.Unidad;

import com.craftbeerstore.application.domain.enumeration.TipoInsumo;

/**
 * A CompraInsumoDetalle.
 */
@Entity
@Table(name = "compra_insumo_detalle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompraInsumoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad")
    private Unidad unidad;

    @Column(name = "codigo_referencia")
    private String codigoReferencia;

    @Column(name = "stock", precision = 10, scale = 2)
    private BigDecimal stock;

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "precio_total", precision = 10, scale = 2)
    private BigDecimal precioTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoInsumo tipo;

    @ManyToOne
    @JsonIgnoreProperties("compraInsumoDetalles")
    private CompraInsumo compraInsumo;

    @ManyToOne
    @JsonIgnoreProperties("compraInsumoDetalles")
    private InsumoRecomendado insumoRecomendado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public CompraInsumoDetalle unidad(Unidad unidad) {
        this.unidad = unidad;
        return this;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public String getCodigoReferencia() {
        return codigoReferencia;
    }

    public CompraInsumoDetalle codigoReferencia(String codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
        return this;
    }

    public void setCodigoReferencia(String codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public CompraInsumoDetalle stock(BigDecimal stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public CompraInsumoDetalle precioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
        return this;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public CompraInsumoDetalle precioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
        return this;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public TipoInsumo getTipo() {
        return tipo;
    }

    public CompraInsumoDetalle tipo(TipoInsumo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoInsumo tipo) {
        this.tipo = tipo;
    }

    public CompraInsumo getCompraInsumo() {
        return compraInsumo;
    }

    public CompraInsumoDetalle compraInsumo(CompraInsumo compraInsumo) {
        this.compraInsumo = compraInsumo;
        return this;
    }

    public void setCompraInsumo(CompraInsumo compraInsumo) {
        this.compraInsumo = compraInsumo;
    }

    public InsumoRecomendado getInsumoRecomendado() {
        return insumoRecomendado;
    }

    public CompraInsumoDetalle insumoRecomendado(InsumoRecomendado insumoRecomendado) {
        this.insumoRecomendado = insumoRecomendado;
        return this;
    }

    public void setInsumoRecomendado(InsumoRecomendado insumoRecomendado) {
        this.insumoRecomendado = insumoRecomendado;
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
        CompraInsumoDetalle compraInsumoDetalle = (CompraInsumoDetalle) o;
        if (compraInsumoDetalle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compraInsumoDetalle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompraInsumoDetalle{" +
            "id=" + getId() +
            ", unidad='" + getUnidad() + "'" +
            ", codigoReferencia='" + getCodigoReferencia() + "'" +
            ", stock=" + getStock() +
            ", precioUnitario=" + getPrecioUnitario() +
            ", precioTotal=" + getPrecioTotal() +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
