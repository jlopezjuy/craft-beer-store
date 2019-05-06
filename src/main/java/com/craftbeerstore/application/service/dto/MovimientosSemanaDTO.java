package com.craftbeerstore.application.service.dto;

import com.craftbeerstore.application.domain.enumeration.EstadoMovimiento;
import com.craftbeerstore.application.domain.enumeration.TipoMovimiento;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the Movimientos entity.
 */
public class MovimientosSemanaDTO implements Serializable {

    private static final long serialVersionUID = 3445529071437633324L;

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "tipoMovimiento")
    private TipoMovimiento tipoMovimiento;

    @JsonProperty(value = "fechaMovimiento")
    private LocalDate fechaMovimiento;

    @JsonProperty(value = "total")
    private BigDecimal total;

    public MovimientosSemanaDTO() {
    }

    public MovimientosSemanaDTO(Long id,
        TipoMovimiento tipoMovimiento, LocalDate fechaMovimiento, BigDecimal total) {
        this.id = id;
        this.tipoMovimiento = tipoMovimiento;
        this.fechaMovimiento = fechaMovimiento;
        this.total = total;
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

    @Override
    public String toString() {
        return "MovimientosSemanaDTO{" +
            "id=" + id +
            ", tipoMovimiento=" + tipoMovimiento +
            ", fechaMovimiento=" + fechaMovimiento +
            ", total=" + total +
            '}';
    }
}

