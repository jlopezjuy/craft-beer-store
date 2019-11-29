package com.craftbeerstore.application.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.craftbeerstore.application.domain.enumeration.TipoPresentacion;

/**
 * A DTO for the {@link com.craftbeerstore.application.domain.Presentacion} entity.
 */
public class PresentacionDTO implements Serializable {

    private Long id;

    @NotNull
    private TipoPresentacion tipoPresentacion;

    @NotNull
    private Long cantidad;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private BigDecimal costoUnitario;

    @NotNull
    private BigDecimal precioVentaUnitario;

    @NotNull
    private BigDecimal precioVentaTotal;

    @NotNull
    private BigDecimal precioCostoTotal;


    private Long productoId;

    private String productoNombreComercial;

    private Long loteId;

    private String loteCodigo;

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

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getPrecioVentaUnitario() {
        return precioVentaUnitario;
    }

    public void setPrecioVentaUnitario(BigDecimal precioVentaUnitario) {
        this.precioVentaUnitario = precioVentaUnitario;
    }

    public BigDecimal getPrecioVentaTotal() {
        return precioVentaTotal;
    }

    public void setPrecioVentaTotal(BigDecimal precioVentaTotal) {
        this.precioVentaTotal = precioVentaTotal;
    }

    public BigDecimal getPrecioCostoTotal() {
        return precioCostoTotal;
    }

    public void setPrecioCostoTotal(BigDecimal precioCostoTotal) {
        this.precioCostoTotal = precioCostoTotal;
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

    public Long getLoteId() {
        return loteId;
    }

    public void setLoteId(Long loteId) {
        this.loteId = loteId;
    }

    public String getLoteCodigo() {
        return loteCodigo;
    }

    public void setLoteCodigo(String loteCodigo) {
        this.loteCodigo = loteCodigo;
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
            ", costoUnitario=" + getCostoUnitario() +
            ", precioVentaUnitario=" + getPrecioVentaUnitario() +
            ", precioVentaTotal=" + getPrecioVentaTotal() +
            ", precioCostoTotal=" + getPrecioCostoTotal() +
            ", producto=" + getProductoId() +
            ", producto='" + getProductoNombreComercial() + "'" +
            ", lote=" + getLoteId() +
            ", lote='" + getLoteCodigo() + "'" +
            "}";
    }
}
