package com.craftbeerstore.application.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.craftbeerstore.application.domain.Estilos} entity.
 */
public class EstilosDTO implements Serializable {

    private Long id;

    private String number;

    private String nombreEstilo;

    private String categoriaEstilo;

    private BigDecimal abvMin;

    private BigDecimal abvMax;

    private BigDecimal abvMed;

    private BigDecimal ibuMin;

    private BigDecimal ibuMax;

    private BigDecimal ibuMed;

    private BigDecimal srmMin;

    private BigDecimal srmMax;

    private BigDecimal srmMed;

    private String descripcion;

    private String ejemploNombreComercial;

    private String ejemploImagenComercial;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNombreEstilo() {
        return nombreEstilo;
    }

    public void setNombreEstilo(String nombreEstilo) {
        this.nombreEstilo = nombreEstilo;
    }

    public String getCategoriaEstilo() {
        return categoriaEstilo;
    }

    public void setCategoriaEstilo(String categoriaEstilo) {
        this.categoriaEstilo = categoriaEstilo;
    }

    public BigDecimal getAbvMin() {
        return abvMin;
    }

    public void setAbvMin(BigDecimal abvMin) {
        this.abvMin = abvMin;
    }

    public BigDecimal getAbvMax() {
        return abvMax;
    }

    public void setAbvMax(BigDecimal abvMax) {
        this.abvMax = abvMax;
    }

    public BigDecimal getAbvMed() {
        return abvMed;
    }

    public void setAbvMed(BigDecimal abvMed) {
        this.abvMed = abvMed;
    }

    public BigDecimal getIbuMin() {
        return ibuMin;
    }

    public void setIbuMin(BigDecimal ibuMin) {
        this.ibuMin = ibuMin;
    }

    public BigDecimal getIbuMax() {
        return ibuMax;
    }

    public void setIbuMax(BigDecimal ibuMax) {
        this.ibuMax = ibuMax;
    }

    public BigDecimal getIbuMed() {
        return ibuMed;
    }

    public void setIbuMed(BigDecimal ibuMed) {
        this.ibuMed = ibuMed;
    }

    public BigDecimal getSrmMin() {
        return srmMin;
    }

    public void setSrmMin(BigDecimal srmMin) {
        this.srmMin = srmMin;
    }

    public BigDecimal getSrmMax() {
        return srmMax;
    }

    public void setSrmMax(BigDecimal srmMax) {
        this.srmMax = srmMax;
    }

    public BigDecimal getSrmMed() {
        return srmMed;
    }

    public void setSrmMed(BigDecimal srmMed) {
        this.srmMed = srmMed;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEjemploNombreComercial() {
        return ejemploNombreComercial;
    }

    public void setEjemploNombreComercial(String ejemploNombreComercial) {
        this.ejemploNombreComercial = ejemploNombreComercial;
    }

    public String getEjemploImagenComercial() {
        return ejemploImagenComercial;
    }

    public void setEjemploImagenComercial(String ejemploImagenComercial) {
        this.ejemploImagenComercial = ejemploImagenComercial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstilosDTO estilosDTO = (EstilosDTO) o;
        if (estilosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estilosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstilosDTO{" +
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
