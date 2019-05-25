package com.craftbeerstore.application.service.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.craftbeerstore.application.domain.enumeration.TipoInsumo;

/**
 * A DTO for the RecetaInsumo entity.
 */
public class RecetaInsumoDTO implements Serializable {

    private Long id;

    private TipoInsumo tipoInsumo;

    private BigDecimal cantidad;


    private Long insumoId;

    private Long recetaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoInsumo getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(TipoInsumo tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Long getInsumoId() {
        return insumoId;
    }

    public void setInsumoId(Long insumoId) {
        this.insumoId = insumoId;
    }

    public Long getRecetaId() {
        return recetaId;
    }

    public void setRecetaId(Long recetaId) {
        this.recetaId = recetaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecetaInsumoDTO recetaInsumoDTO = (RecetaInsumoDTO) o;
        if (recetaInsumoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recetaInsumoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecetaInsumoDTO{" +
            "id=" + getId() +
            ", tipoInsumo='" + getTipoInsumo() + "'" +
            ", cantidad=" + getCantidad() +
            ", insumo=" + getInsumoId() +
            ", receta=" + getRecetaId() +
            "}";
    }
}
