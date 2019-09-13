package com.craftbeerstore.application.service.dto;

import com.craftbeerstore.application.domain.enumeration.TipoMovimientoCaja;
import com.craftbeerstore.application.domain.enumeration.TipoPago;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the Caja entity.
 */
public class CajaDTO implements Serializable {

  private Long id;

  @NotNull
  private TipoMovimientoCaja tipoMovimiento;

  @NotNull
  private TipoPago tipoPago;

  @Lob
  private String descripcion;

  private BigDecimal saldoCtaCte;

  @NotNull
  private BigDecimal importe;

  @NotNull
  private LocalDate fecha;


  private Long proveedorId;

  private String proveedorNombreProveedor;

  private Long clienteId;

  private String clienteNombreApellido;

  private Long empresaId;

  public CajaDTO() {
  }

  public CajaDTO(@NotNull BigDecimal importe, @NotNull LocalDate fecha) {
    this.importe = importe;
    this.fecha = fecha;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public TipoMovimientoCaja getTipoMovimiento() {
    return tipoMovimiento;
  }

  public void setTipoMovimiento(TipoMovimientoCaja tipoMovimiento) {
    this.tipoMovimiento = tipoMovimiento;
  }

  public TipoPago getTipoPago() {
    return tipoPago;
  }

  public void setTipoPago(TipoPago tipoPago) {
    this.tipoPago = tipoPago;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public BigDecimal getSaldoCtaCte() {
    return saldoCtaCte;
  }

  public void setSaldoCtaCte(BigDecimal saldoCtaCte) {
    this.saldoCtaCte = saldoCtaCte;
  }

  public BigDecimal getImporte() {
    return importe;
  }

  public void setImporte(BigDecimal importe) {
    this.importe = importe;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public Long getProveedorId() {
    return proveedorId;
  }

  public void setProveedorId(Long proveedorId) {
    this.proveedorId = proveedorId;
  }

  public String getProveedorNombreProveedor() {
    return proveedorNombreProveedor;
  }

  public void setProveedorNombreProveedor(String proveedorNombreProveedor) {
    this.proveedorNombreProveedor = proveedorNombreProveedor;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CajaDTO cajaDTO = (CajaDTO) o;
    if (cajaDTO.getId() == null || getId() == null) {
      return false;
    }
    return Objects.equals(getId(), cajaDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "CajaDTO{" +
      "id=" + getId() +
      ", tipoMovimiento='" + getTipoMovimiento() + "'" +
      ", tipoPago='" + getTipoPago() + "'" +
      ", descripcion='" + getDescripcion() + "'" +
      ", saldoCtaCte=" + getSaldoCtaCte() +
      ", importe=" + getImporte() +
      ", fecha='" + getFecha() + "'" +
      ", proveedor=" + getProveedorId() +
      ", proveedor='" + getProveedorNombreProveedor() + "'" +
      ", cliente=" + getClienteId() +
      ", cliente='" + getClienteNombreApellido() + "'" +
      ", empresa=" + getEmpresaId() +
      "}";
  }
}
