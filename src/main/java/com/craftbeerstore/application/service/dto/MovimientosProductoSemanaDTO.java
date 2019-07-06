package com.craftbeerstore.application.service.dto;

import com.craftbeerstore.application.domain.enumeration.TipoMovimiento;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the Movimientos entity.
 */
public class MovimientosProductoSemanaDTO implements Serializable {

    private static final long serialVersionUID = -422259743194699022L;

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "tipoMovimiento")
    private TipoMovimiento tipoMovimiento;

    @JsonProperty(value = "fechaMovimiento")
    private LocalDate fechaMovimiento;

    @JsonProperty(value = "total")
    private BigDecimal total;

    @JsonProperty(value = "cantidad")
    private Long cantidad;

    @JsonProperty(value = "nombreProducto")
    private String nombreProducto;

    @JsonProperty(value = "color")
    private String color;


    public MovimientosProductoSemanaDTO() {
    }

  public MovimientosProductoSemanaDTO(Long id, TipoMovimiento tipoMovimiento, LocalDate fechaMovimiento,
                                      BigDecimal total, Long cantidad, String nombreProducto, String color) {
    this.id = id;
    this.tipoMovimiento = tipoMovimiento;
    this.fechaMovimiento = fechaMovimiento;
    this.total = total;
    this.cantidad = cantidad;
    this.nombreProducto = nombreProducto;
    this.color = color;
  }

  public MovimientosProductoSemanaDTO(Long id,
                                      TipoMovimiento tipoMovimiento, LocalDate fechaMovimiento, BigDecimal total,
                                      Long cantidad, String nombreProducto) {
        this.id = id;
        this.tipoMovimiento = tipoMovimiento;
        this.fechaMovimiento = fechaMovimiento;
        this.total = total;
        this.cantidad = cantidad;
        this.nombreProducto = nombreProducto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(
        TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public LocalDate getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(LocalDate fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreProducto() {

        return nombreProducto;
    }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    @Override
    public String
    toString() {
        return "MovimientosProductoSemanaDTO{" +
            "id=" + id +
            ", tipoMovimiento=" + tipoMovimiento +
            ", fechaMovimiento=" + fechaMovimiento +
            ", total=" + total +
            ", cantidad=" + cantidad +
            ", nombreProducto='" + nombreProducto + '\'' +
            '}';
    }
}

