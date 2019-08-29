package com.craftbeerstore.application.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.craftbeerstore.application.domain.enumeration.TipoMovimiento;
import com.craftbeerstore.application.domain.enumeration.EstadoMovimiento;

/**
 * A DTO for the Movimientos entity.
 */
public class MovimientosDTO implements Serializable {

    private Long id;

    @NotNull
    private TipoMovimiento tipoMovimiento;

    @NotNull
    private LocalDate fechaMovimiento;

    @NotNull
    private BigDecimal precioTotal;

    @NotNull
    private String numeroMovimiento;

    @NotNull
    private EstadoMovimiento estado;

    private BigDecimal litrosTotales;


    private Long clienteId;

    private String clienteNombreApellido;

    private Long empresaId;

    private String empresaNombreEmpresa;

    private Long puntoDeVentaId;

  public MovimientosDTO() {
  }

  public MovimientosDTO(@NotNull LocalDate fechaMovimiento, @NotNull BigDecimal precioTotal) {
    this.fechaMovimiento = fechaMovimiento;
    this.precioTotal = precioTotal;
  }

  public MovimientosDTO(Long id, @NotNull TipoMovimiento tipoMovimiento, @NotNull LocalDate fechaMovimiento,
                        @NotNull BigDecimal precioTotal, @NotNull String numeroMovimiento,
                        @NotNull EstadoMovimiento estado, BigDecimal litrosTotales,
                        Long clienteId, String clienteNombreApellido, Long empresaId,
                        String empresaNombreEmpresa, Long puntoDeVentaId) {
    this.id = id;
    this.tipoMovimiento = tipoMovimiento;
    this.fechaMovimiento = fechaMovimiento;
    this.precioTotal = precioTotal;
    this.numeroMovimiento = numeroMovimiento;
    this.estado = estado;
    this.litrosTotales = litrosTotales;
    this.clienteId = clienteId;
    this.clienteNombreApellido = clienteNombreApellido;
    this.empresaId = empresaId;
    this.empresaNombreEmpresa = empresaNombreEmpresa;
    this.puntoDeVentaId = puntoDeVentaId;
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

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
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

    public String getNumeroMovimiento() {
        return numeroMovimiento;
    }

    public void setNumeroMovimiento(String numeroMovimiento) {
        this.numeroMovimiento = numeroMovimiento;
    }

    public EstadoMovimiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoMovimiento estado) {
        this.estado = estado;
    }

    public BigDecimal getLitrosTotales() {
        return litrosTotales;
    }

    public void setLitrosTotales(BigDecimal litrosTotales) {
        this.litrosTotales = litrosTotales;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombreApellido() {
        return clienteNombreApellido;
    }

    public void setClienteNombreApellido(String clienteNombreApellido) {
        this.clienteNombreApellido = clienteNombreApellido;
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

    public Long getPuntoDeVentaId() {
        return puntoDeVentaId;
    }

    public void setPuntoDeVentaId(Long puntoDeVentaId) {
        this.puntoDeVentaId = puntoDeVentaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MovimientosDTO movimientosDTO = (MovimientosDTO) o;
        if (movimientosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movimientosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovimientosDTO{" +
            "id=" + getId() +
            ", tipoMovimiento='" + getTipoMovimiento() + "'" +
            ", fechaMovimiento='" + getFechaMovimiento() + "'" +
            ", precioTotal=" + getPrecioTotal() +
            ", numeroMovimiento='" + getNumeroMovimiento() + "'" +
            ", estado='" + getEstado() + "'" +
            ", litrosTotales=" + getLitrosTotales() +
            ", cliente=" + getClienteId() +
            ", cliente='" + getClienteNombreApellido() + "'" +
            ", empresa=" + getEmpresaId() +
            ", empresa='" + getEmpresaNombreEmpresa() + "'" +
            ", puntoDeVenta=" + getPuntoDeVentaId() +
            "}";
    }
}
