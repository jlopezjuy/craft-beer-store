package com.craftbeerstore.application.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.craftbeerstore.application.domain.enumeration.TipoPresentacion;

/**
 * A DTO for the Presentacion entity.
 */
public class PresentacionDTO implements Serializable {

    private Long id;

    @NotNull
    private TipoPresentacion tipoPresentacion;

    @NotNull
    private Long cantidad;

    @NotNull
    private LocalDate fecha;


    private Long productoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPresentacion getTipoPresentacion() {
        return tipoPresentacion;
    }

    public void setTipoPresentacion(TipoPresentacion tipoPresentacion) {
        this.tipoPresentacion = tipoPresentacion;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PresentacionDTO presentacionDTO = (PresentacionDTO) o;
        if (presentacionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), presentacionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PresentacionDTO{" +
            "id=" + getId() +
            ", tipoPresentacion='" + getTipoPresentacion() + "'" +
            ", cantidad=" + getCantidad() +
            ", fecha='" + getFecha() + "'" +
            ", producto=" + getProductoId() +
            "}";
    }
}
