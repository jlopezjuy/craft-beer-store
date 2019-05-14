package com.craftbeerstore.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.craftbeerstore.application.domain.enumeration.EstiloCerveza;

import com.craftbeerstore.application.domain.enumeration.TipoProducto;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private EstiloCerveza tipo;

    @Column(name = "nombre_comercial")
    private String nombreComercial;

    @NotNull
    @Column(name = "precio_litro", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioLitro;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_producto")
    private TipoProducto tipoProducto;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @Lob
    @Column(name = "observacion")
    private String observacion;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private Empresa empresa;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private Estilos estilos;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstiloCerveza getTipo() {
        return tipo;
    }

    public Producto tipo(EstiloCerveza tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(EstiloCerveza tipo) {
        this.tipo = tipo;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public Producto nombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
        return this;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public BigDecimal getPrecioLitro() {
        return precioLitro;
    }

    public Producto precioLitro(BigDecimal precioLitro) {
        this.precioLitro = precioLitro;
        return this;
    }

    public void setPrecioLitro(BigDecimal precioLitro) {
        this.precioLitro = precioLitro;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public Producto tipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
        return this;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Producto imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Producto imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public String getObservacion() {
        return observacion;
    }

    public Producto observacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Producto empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Estilos getEstilos() {
        return estilos;
    }

    public Producto estilos(Estilos estilos) {
        this.estilos = estilos;
        return this;
    }

    public void setEstilos(Estilos estilos) {
        this.estilos = estilos;
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
        Producto producto = (Producto) o;
        if (producto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), producto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", nombreComercial='" + getNombreComercial() + "'" +
            ", precioLitro=" + getPrecioLitro() +
            ", tipoProducto='" + getTipoProducto() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            ", observacion='" + getObservacion() + "'" +
            "}";
    }
}
