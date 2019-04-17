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


    private Long clienteId;

    private String clienteNombeApellido;

    private Long empresaId;

    private String empresaNombreEmpresa;

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

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombeApellido() {
        return clienteNombeApellido;
    }

    public void setClienteNombeApellido(String clienteNombeApellido) {
        this.clienteNombeApellido = clienteNombeApellido;
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
            ", cliente=" + getClienteId() +
            ", cliente='" + getClienteNombeApellido() + "'" +
            ", empresa=" + getEmpresaId() +
            ", empresa='" + getEmpresaNombreEmpresa() + "'" +
            "}";
    }
}
