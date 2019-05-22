package com.craftbeerstore.application.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import com.craftbeerstore.application.domain.enumeration.TipoEquipamiento;

/**
 * A DTO for the Equipamiento entity.
 */
public class EquipamientoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreEquipamiento;

    @NotNull
    private TipoEquipamiento tipoEquipamiento;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal precio;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal costoEnvio;

    @NotNull
    private LocalDate fechaCompra;

    @Lob
    private byte[] imagen;

    private String imagenContentType;

    private Long empresaId;

    private String empresaNombreEmpresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEquipamiento() {
        return nombreEquipamiento;
    }

    public void setNombreEquipamiento(String nombreEquipamiento) {
        this.nombreEquipamiento = nombreEquipamiento;
    }

    public TipoEquipamiento getTipoEquipamiento() {
        return tipoEquipamiento;
    }

    public void setTipoEquipamiento(TipoEquipamiento tipoEquipamiento) {
        this.tipoEquipamiento = tipoEquipamiento;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(BigDecimal costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public String getEmpresaNombreEmpresa() {
        return empresaNombreEmpresa;
    }

    public void setEmpresaNombreEmpresa(String empresaNombreEmpresa) {
        this.empresaNombreEmpresa = empresaNombreEmpresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EquipamientoDTO equipamientoDTO = (EquipamientoDTO) o;
        if (equipamientoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipamientoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EquipamientoDTO{" +
            "id=" + getId() +
            ", nombreEquipamiento='" + getNombreEquipamiento() + "'" +
            ", tipoEquipamiento='" + getTipoEquipamiento() + "'" +
            ", precio=" + getPrecio() +
            ", costoEnvio=" + getCostoEnvio() +
            ", fechaCompra='" + getFechaCompra() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", empresa=" + getEmpresaId() +
            ", empresa='" + getEmpresaNombreEmpresa() + "'" +
            "}";
    }
}
