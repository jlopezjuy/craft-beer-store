package com.craftbeerstore.application.service.dto;

import com.craftbeerstore.application.domain.enumeration.TipoInsumo;
import com.craftbeerstore.application.domain.enumeration.Unidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the CompraInsumoDetalle entity.
 */
public class CompraInsumoDetalleDTO implements Serializable {

  private Long id;

  private Unidad unidad;

  private String codigoReferencia;

  private BigDecimal stock;

  private BigDecimal precio;

  private TipoInsumo tipo;


  private Long compraInsumoId;

  private String compraInsumoNroFactura;

  private Long insumoRecomendadoId;

  private String insumoRecomendadoNombre;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Unidad getUnidad() {
    return unidad;
  }

  public void setUnidad(Unidad unidad) {
    this.unidad = unidad;
  }

  public String getCodigoReferencia() {
    return codigoReferencia;
  }

  public void setCodigoReferencia(String codigoReferencia) {
    this.codigoReferencia = codigoReferencia;
  }

  public BigDecimal getStock() {
    return stock;
  }

  public void setStock(BigDecimal stock) {
    this.stock = stock;
  }

  public BigDecimal getPrecio() {
    return precio;
  }

  public void setPrecio(BigDecimal precio) {
    this.precio = precio;
  }

  public TipoInsumo getTipo() {
    return tipo;
  }

  public void setTipo(TipoInsumo tipo) {
    this.tipo = tipo;
  }

  public Long getCompraInsumoId() {
    return compraInsumoId;
  }

  public void setCompraInsumoId(Long compraInsumoId) {
    this.compraInsumoId = compraInsumoId;
  }

  public String getCompraInsumoNroFactura() {
    return compraInsumoNroFactura;
  }

  public void setCompraInsumoNroFactura(String compraInsumoNroFactura) {
    this.compraInsumoNroFactura = compraInsumoNroFactura;
  }

  public Long getInsumoRecomendadoId() {
    return insumoRecomendadoId;
  }

  public void setInsumoRecomendadoId(Long insumoRecomendadoId) {
    this.insumoRecomendadoId = insumoRecomendadoId;
  }

  public String getInsumoRecomendadoNombre() {
    return insumoRecomendadoNombre;
  }

  public void setInsumoRecomendadoNombre(String insumoRecomendadoNombre) {
    this.insumoRecomendadoNombre = insumoRecomendadoNombre;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CompraInsumoDetalleDTO compraInsumoDetalleDTO = (CompraInsumoDetalleDTO) o;
    if (compraInsumoDetalleDTO.getId() == null || getId() == null) {
      return false;
    }
    return Objects.equals(getId(), compraInsumoDetalleDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "CompraInsumoDetalleDTO{" +
      "id=" + getId() +
      ", unidad='" + getUnidad() + "'" +
      ", codigoReferencia='" + getCodigoReferencia() + "'" +
      ", stock=" + getStock() +
      ", precio=" + getPrecio() +
      ", tipo='" + getTipo() + "'" +
      ", compraInsumo=" + getCompraInsumoId() +
      ", compraInsumo='" + getCompraInsumoNroFactura() + "'" +
      ", insumoRecomendado=" + getInsumoRecomendadoId() +
      ", insumoRecomendado='" + getInsumoRecomendadoNombre() + "'" +
      "}";
  }
}
