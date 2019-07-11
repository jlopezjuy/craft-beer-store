package com.craftbeerstore.application.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the Movimientos entity.
 */
public class MovimientosVentasDTO implements Serializable {


  private LocalDate fechaMovimiento;


  private BigDecimal precioTotal;


  public MovimientosVentasDTO() {
  }

  public MovimientosVentasDTO(@NotNull LocalDate fechaMovimiento, @NotNull BigDecimal precioTotal) {
    this.fechaMovimiento = fechaMovimiento;
    this.precioTotal = precioTotal;
  }


  public LocalDate getFechaMovimiento() {
    return fechaMovimiento;
  }

  public void setFechaMovimiento(LocalDate fechaMovimiento) {
    this.fechaMovimiento = fechaMovimiento;
  }

  public BigDecimal getPrecioTotal() {
    return precioTotal;
  }

  public void setPrecioTotal(BigDecimal precioTotal) {
    this.precioTotal = precioTotal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MovimientosVentasDTO that = (MovimientosVentasDTO) o;
    return Objects.equals(fechaMovimiento, that.fechaMovimiento) &&
      Objects.equals(precioTotal, that.precioTotal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fechaMovimiento, precioTotal);
  }

  @Override
  public String toString() {
    return "MovimientosVentasDTO{" +
      "fechaMovimiento=" + fechaMovimiento +
      ", precioTotal=" + precioTotal +
      '}';
  }
}
