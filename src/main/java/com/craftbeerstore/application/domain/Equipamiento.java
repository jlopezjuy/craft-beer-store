package com.craftbeerstore.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.craftbeerstore.application.domain.enumeration.TipoEquipamiento;

/**
 * A Equipamiento.
 */
@Entity
@Table(name = "equipamiento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Equipamiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre_equipamiento", nullable = false)
    private String nombreEquipamiento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_equipamiento", nullable = false)
    private TipoEquipamiento tipoEquipamiento;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "precio", precision = 21, scale = 2, nullable = false)
    private BigDecimal precio;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "costo_envio", precision = 21, scale = 2, nullable = false)
    private BigDecimal costoEnvio;

    @NotNull
    @Column(name = "fecha_compra", nullable = false)
    private LocalDate fechaCompra;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @ManyToOne
    @JsonIgnoreProperties("equipamientos")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEquipamiento() {
        return nombreEquipamiento;
    }

    public Equipamiento nombreEquipamiento(String nombreEquipamiento) {
        this.nombreEquipamiento = nombreEquipamiento;
        return this;
    }

    public void setNombreEquipamiento(String nombreEquipamiento) {
        this.nombreEquipamiento = nombreEquipamiento;
    }

    public TipoEquipamiento getTipoEquipamiento() {
        return tipoEquipamiento;
    }

    public Equipamiento tipoEquipamiento(TipoEquipamiento tipoEquipamiento) {
        this.tipoEquipamiento = tipoEquipamiento;
        return this;
    }

    public void setTipoEquipamiento(TipoEquipamiento tipoEquipamiento) {
        this.tipoEquipamiento = tipoEquipamiento;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public Equipamiento precio(BigDecimal precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getCostoEnvio() {
        return costoEnvio;
    }

    public Equipamiento costoEnvio(BigDecimal costoEnvio) {
        this.costoEnvio = costoEnvio;
        return this;
    }

    public void setCostoEnvio(BigDecimal costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public Equipamiento fechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
        return this;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Equipamiento imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Equipamiento imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Equipamiento empresa(Empresa empresa) {
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
        if (!(o instanceof Equipamiento)) {
            return false;
        }
        return id != null && id.equals(((Equipamiento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Equipamiento{" +
            "id=" + getId() +
            ", nombreEquipamiento='" + getNombreEquipamiento() + "'" +
            ", tipoEquipamiento='" + getTipoEquipamiento() + "'" +
            ", precio=" + getPrecio() +
            ", costoEnvio=" + getCostoEnvio() +
            ", fechaCompra='" + getFechaCompra() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            "}";
    }
}
