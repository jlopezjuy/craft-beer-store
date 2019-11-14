package com.craftbeerstore.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.craftbeerstore.application.domain.enumeration.Unidad;

import com.craftbeerstore.application.domain.enumeration.TipoInsumo;

/**
 * A Insumo.
 */
@Entity
@Table(name = "insumo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Insumo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre_insumo", nullable = false, unique = true)
    private String nombreInsumo;

    @NotNull
    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "stock", precision = 10, scale = 2)
    private BigDecimal stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad")
    private Unidad unidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoInsumo tipo;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "precio_total", precision = 10, scale = 2)
    private BigDecimal precioTotal;

    @ManyToOne
    @JsonIgnoreProperties("insumos")
    private Empresa empresa;

    @ManyToOne
    @JsonIgnoreProperties("insumos")
    private InsumoRecomendado insumoRecomendado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public Insumo nombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
        return this;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    public String getMarca() {
        return marca;
    }

    public Insumo marca(String marca) {
        this.marca = marca;
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public Insumo stock(BigDecimal stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public Insumo unidad(Unidad unidad) {
        this.unidad = unidad;
        return this;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public TipoInsumo getTipo() {
        return tipo;
    }

    public Insumo tipo(TipoInsumo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoInsumo tipo) {
        this.tipo = tipo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Insumo imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Insumo imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public Insumo precioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
        return this;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public Insumo precioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
        return this;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Insumo empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public InsumoRecomendado getInsumoRecomendado() {
        return insumoRecomendado;
    }

    public Insumo insumoRecomendado(InsumoRecomendado insumoRecomendado) {
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
        Insumo insumo = (Insumo) o;
        if (insumo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insumo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Insumo{" +
            "id=" + getId() +
            ", nombreInsumo='" + getNombreInsumo() + "'" +
            ", marca='" + getMarca() + "'" +
            ", stock=" + getStock() +
            ", unidad='" + getUnidad() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            ", precioUnitario=" + getPrecioUnitario() +
            ", precioTotal=" + getPrecioTotal() +
            "}";
    }
}
