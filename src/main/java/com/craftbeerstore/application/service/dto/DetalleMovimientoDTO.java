package com.craftbeerstore.application.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the DetalleMovimiento entity.
 */
public class DetalleMovimientoDTO implements Serializable {

  private Long id;

  @NotNull
  private Long cantidad;

  @NotNull
  private BigDecimal precioTotal;


  private Long presentacionId;

  private Long movimientosId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCantidad() {
    return cantidad;
  }

  public void setCantidad(Long cantidad) {
    this.cantidad = cantidad;
  }

  public BigDecimal getPrecioTotal() {
    return precioTotal;
  }

  public void setPrecioTotal(BigDecimal precioTotal) {
    this.precioTotal = precioTotal;
  }

  public Long getPresentacionId() {
    return presentacionId;
  }

  public void setPresentacionId(Long presentacionId) {
    this.presentacionId = presentacionId;
  }

  public Long getMovimientosId() {
    return movimientosId;
  }

  public void setMovimientosId(Long movimientosId) {
    this.movimientosId = movimientosId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    DetalleMovimientoDTO detalleMovimientoDTO = (DetalleMovimientoDTO) o;
    if (detalleMovimientoDTO.getId() == null || getId() == null) {
      return false;
    }
    return Objects.equals(getId(), detalleMovimientoDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "DetalleMovimientoDTO{" +
      "id=" + getId() +
      ", cantidad=" + getCantidad() +
      ", precioTotal=" + getPrecioTotal() +
      ", presentacion=" + getPresentacionId() +
      ", movimientos=" + getMovimientosId() +
      "}";
  }
}
