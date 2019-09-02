package com.craftbeerstore.application.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the Movimientos entity.
 */
public class MovimientoLitroDTO implements Serializable {


  private LocalDate fechaMovimiento;


  private BigDecimal litroTotal;


  public MovimientoLitroDTO() {
  }

  public MovimientoLitroDTO(@NotNull LocalDate fechaMovimiento, @NotNull BigDecimal precioTotal) {
    this.fechaMovimiento = fechaMovimiento;
    this.litroTotal = precioTotal;
  }


  public LocalDate getFechaMovimiento() {
    return fechaMovimiento;
  }

  public void setFechaMovimiento(LocalDate fechaMovimiento) {
    this.fechaMovimiento = fechaMovimiento;
  }

  public BigDecimal getLitroTotal() {
    return litroTotal;
  }

  public void setLitroTotal(BigDecimal litroTotal) {
    this.litroTotal = litroTotal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MovimientoLitroDTO that = (MovimientoLitroDTO) o;
    return Objects.equals(fechaMovimiento, that.fechaMovimiento) &&
      Objects.equals(litroTotal, that.litroTotal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fechaMovimiento, litroTotal);
  }

  @Override
  public String toString() {
    return "MovimientosVentasDTO{" +
      "fechaMovimiento=" + fechaMovimiento +
      ", litroTotal=" + litroTotal +
      '}';
  }
}
