package com.craftbeerstore.application.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the Receta entity.
 */
public class RecetaDTO implements Serializable {

    private Long id;

    private String nombre;

    private String brewMaster;

    private BigDecimal batch;

    private BigDecimal temperaturaDeMacerado;

    private BigDecimal og;

    private BigDecimal fg;

    private BigDecimal abv;

    private BigDecimal ibu;

    private BigDecimal srm;

    private BigDecimal empaste;

    private BigDecimal temperaturaMacerado;

    private LocalDate fecha;


    private Long productoId;

    private String productoNombreComercial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBrewMaster() {
        return brewMaster;
    }

    public void setBrewMaster(String brewMaster) {
        this.brewMaster = brewMaster;
    }

    public BigDecimal getBatch() {
        return batch;
    }

    public void setBatch(BigDecimal batch) {
        this.batch = batch;
    }

    public BigDecimal getTemperaturaDeMacerado() {
        return temperaturaDeMacerado;
    }

    public void setTemperaturaDeMacerado(BigDecimal temperaturaDeMacerado) {
        this.temperaturaDeMacerado = temperaturaDeMacerado;
    }

    public BigDecimal getOg() {
        return og;
    }

    public void setOg(BigDecimal og) {
        this.og = og;
    }

    public BigDecimal getFg() {
        return fg;
    }

    public void setFg(BigDecimal fg) {
        this.fg = fg;
    }

    public BigDecimal getAbv() {
        return abv;
    }

    public void setAbv(BigDecimal abv) {
        this.abv = abv;
    }

    public BigDecimal getIbu() {
        return ibu;
    }

    public void setIbu(BigDecimal ibu) {
        this.ibu = ibu;
    }

    public BigDecimal getSrm() {
        return srm;
    }

    public void setSrm(BigDecimal srm) {
        this.srm = srm;
    }

    public BigDecimal getEmpaste() {
        return empaste;
    }

    public void setEmpaste(BigDecimal empaste) {
        this.empaste = empaste;
    }

    public BigDecimal getTemperaturaMacerado() {
        return temperaturaMacerado;
    }

    public void setTemperaturaMacerado(BigDecimal temperaturaMacerado) {
        this.temperaturaMacerado = temperaturaMacerado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getProductoNombreComercial() {
        return productoNombreComercial;
    }

    public void setProductoNombreComercial(String productoNombreComercial) {
        this.productoNombreComercial = productoNombreComercial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecetaDTO recetaDTO = (RecetaDTO) o;
        if (recetaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recetaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecetaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", brewMaster='" + getBrewMaster() + "'" +
            ", batch=" + getBatch() +
            ", temperaturaDeMacerado=" + getTemperaturaDeMacerado() +
            ", og=" + getOg() +
            ", fg=" + getFg() +
            ", abv=" + getAbv() +
            ", ibu=" + getIbu() +
            ", srm=" + getSrm() +
            ", empaste=" + getEmpaste() +
            ", temperaturaMacerado=" + getTemperaturaMacerado() +
            ", fecha='" + getFecha() + "'" +
            ", producto=" + getProductoId() +
            ", producto='" + getProductoNombreComercial() + "'" +
            "}";
    }
}
