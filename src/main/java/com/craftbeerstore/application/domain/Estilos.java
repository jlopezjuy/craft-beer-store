package com.craftbeerstore.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Estilos.
 */
@Entity
@Table(name = "estilos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Estilos implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_number")
    private String number;

    @Column(name = "nombre_estilo")
    private String nombreEstilo;

    @Column(name = "categoria_estilo")
    private String categoriaEstilo;

    @Column(name = "abv_min", precision = 10, scale = 2)
    private BigDecimal abvMin;

    @Column(name = "abv_max", precision = 10, scale = 2)
    private BigDecimal abvMax;

    @Column(name = "abv_med", precision = 10, scale = 2)
    private BigDecimal abvMed;

    @Column(name = "ibu_min", precision = 10, scale = 2)
    private BigDecimal ibuMin;

    @Column(name = "ibu_max", precision = 10, scale = 2)
    private BigDecimal ibuMax;

    @Column(name = "ibu_med", precision = 10, scale = 2)
    private BigDecimal ibuMed;

    @Column(name = "srm_min", precision = 10, scale = 2)
    private BigDecimal srmMin;

    @Column(name = "srm_max", precision = 10, scale = 2)
    private BigDecimal srmMax;

    @Column(name = "srm_med", precision = 10, scale = 2)
    private BigDecimal srmMed;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "ejemplo_nombre_comercial")
    private String ejemploNombreComercial;

    @Column(name = "ejemplo_imagen_comercial")
    private String ejemploImagenComercial;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public Estilos number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNombreEstilo() {
        return nombreEstilo;
    }

    public Estilos nombreEstilo(String nombreEstilo) {
        this.nombreEstilo = nombreEstilo;
        return this;
    }

    public void setNombreEstilo(String nombreEstilo) {
        this.nombreEstilo = nombreEstilo;
    }

    public String getCategoriaEstilo() {
        return categoriaEstilo;
    }

    public Estilos categoriaEstilo(String categoriaEstilo) {
        this.categoriaEstilo = categoriaEstilo;
        return this;
    }

    public void setCategoriaEstilo(String categoriaEstilo) {
        this.categoriaEstilo = categoriaEstilo;
    }

    public BigDecimal getAbvMin() {
        return abvMin;
    }

    public Estilos abvMin(BigDecimal abvMin) {
        this.abvMin = abvMin;
        return this;
    }

    public void setAbvMin(BigDecimal abvMin) {
        this.abvMin = abvMin;
    }

    public BigDecimal getAbvMax() {
        return abvMax;
    }

    public Estilos abvMax(BigDecimal abvMax) {
        this.abvMax = abvMax;
        return this;
    }

    public void setAbvMax(BigDecimal abvMax) {
        this.abvMax = abvMax;
    }

    public BigDecimal getAbvMed() {
        return abvMed;
    }

    public Estilos abvMed(BigDecimal abvMed) {
        this.abvMed = abvMed;
        return this;
    }

    public void setAbvMed(BigDecimal abvMed) {
        this.abvMed = abvMed;
    }

    public BigDecimal getIbuMin() {
        return ibuMin;
    }

    public Estilos ibuMin(BigDecimal ibuMin) {
        this.ibuMin = ibuMin;
        return this;
    }

    public void setIbuMin(BigDecimal ibuMin) {
        this.ibuMin = ibuMin;
    }

    public BigDecimal getIbuMax() {
        return ibuMax;
    }

    public Estilos ibuMax(BigDecimal ibuMax) {
        this.ibuMax = ibuMax;
        return this;
    }

    public void setIbuMax(BigDecimal ibuMax) {
        this.ibuMax = ibuMax;
    }

    public BigDecimal getIbuMed() {
        return ibuMed;
    }

    public Estilos ibuMed(BigDecimal ibuMed) {
        this.ibuMed = ibuMed;
        return this;
    }

    public void setIbuMed(BigDecimal ibuMed) {
        this.ibuMed = ibuMed;
    }

    public BigDecimal getSrmMin() {
        return srmMin;
    }

    public Estilos srmMin(BigDecimal srmMin) {
        this.srmMin = srmMin;
        return this;
    }

    public void setSrmMin(BigDecimal srmMin) {
        this.srmMin = srmMin;
    }

    public BigDecimal getSrmMax() {
        return srmMax;
    }

    public Estilos srmMax(BigDecimal srmMax) {
        this.srmMax = srmMax;
        return this;
    }

    public void setSrmMax(BigDecimal srmMax) {
        this.srmMax = srmMax;
    }

    public BigDecimal getSrmMed() {
        return srmMed;
    }

    public Estilos srmMed(BigDecimal srmMed) {
        this.srmMed = srmMed;
        return this;
    }

    public void setSrmMed(BigDecimal srmMed) {
        this.srmMed = srmMed;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Estilos descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEjemploNombreComercial() {
        return ejemploNombreComercial;
    }

    public Estilos ejemploNombreComercial(String ejemploNombreComercial) {
        this.ejemploNombreComercial = ejemploNombreComercial;
        return this;
    }

    public void setEjemploNombreComercial(String ejemploNombreComercial) {
        this.ejemploNombreComercial = ejemploNombreComercial;
    }

    public String getEjemploImagenComercial() {
        return ejemploImagenComercial;
    }

    public Estilos ejemploImagenComercial(String ejemploImagenComercial) {
        this.ejemploImagenComercial = ejemploImagenComercial;
        return this;
    }

    public void setEjemploImagenComercial(String ejemploImagenComercial) {
        this.ejemploImagenComercial = ejemploImagenComercial;
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
        Estilos estilos = (Estilos) o;
        if (estilos.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estilos.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Estilos{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", nombreEstilo='" + getNombreEstilo() + "'" +
            ", categoriaEstilo='" + getCategoriaEstilo() + "'" +
            ", abvMin=" + getAbvMin() +
            ", abvMax=" + getAbvMax() +
            ", abvMed=" + getAbvMed() +
            ", ibuMin=" + getIbuMin() +
            ", ibuMax=" + getIbuMax() +
            ", ibuMed=" + getIbuMed() +
            ", srmMin=" + getSrmMin() +
            ", srmMax=" + getSrmMax() +
            ", srmMed=" + getSrmMed() +
            ", descripcion='" + getDescripcion() + "'" +
            ", ejemploNombreComercial='" + getEjemploNombreComercial() + "'" +
            ", ejemploImagenComercial='" + getEjemploImagenComercial() + "'" +
            "}";
    }
}
