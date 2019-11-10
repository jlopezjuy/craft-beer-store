package com.craftbeerstore.application.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;

import com.craftbeerstore.application.domain.enumeration.Unidad;
import com.craftbeerstore.application.domain.enumeration.TipoInsumo;

/**
 * A DTO for the Insumo entity.
 */
public class InsumoDTO implements Serializable {

  private Long id;

  @NotNull
  private String nombreInsumo;

  @NotNull
  private String marca;

  private BigDecimal stock;

  private Unidad unidad;

  private TipoInsumo tipo;

  @Lob
  private byte[] imagen;

  private String imagenContentType;
  private BigDecimal precio;


  private Long empresaId;

  private Long insumoRecomendadoId;

  private String insumoRecomendadoNombre;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombreInsumo() {
    return nombreInsumo;
  }

  public void setNombreInsumo(String nombreInsumo) {
    this.nombreInsumo = nombreInsumo;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public BigDecimal getStock() {
    return stock;
  }

  public void setStock(BigDecimal stock) {
    this.stock = stock;
  }

  public Unidad getUnidad() {
    return unidad;
  }

  public void setUnidad(Unidad unidad) {
    this.unidad = unidad;
  }

  public TipoInsumo getTipo() {
    return tipo;
  }

  public void setTipo(TipoInsumo tipo) {
    this.tipo = tipo;
  }

  public byte[] getImagen() {
    return imagen;
  }

  public void setImagen(byte[] imagen) {
    this.imagen = imagen;
  }

  public String getImagenContentType() {
    return imagenContentType;
  }

  public void setImagenContentType(String imagenContentType) {
    this.imagenContentType = imagenContentType;
  }

  public BigDecimal getPrecio() {
    return precio;
  }

  public void setPrecio(BigDecimal precio) {
    this.precio = precio;
  }

  public Long getEmpresaId() {
    return empresaId;
  }

  public void setEmpresaId(Long empresaId) {
    this.empresaId = empresaId;
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

    InsumoDTO insumoDTO = (InsumoDTO) o;
    if (insumoDTO.getId() == null || getId() == null) {
      return false;
    }
    return Objects.equals(getId(), insumoDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "InsumoDTO{" +
      "id=" + getId() +
      ", nombreInsumo='" + getNombreInsumo() + "'" +
      ", marca='" + getMarca() + "'" +
      ", stock=" + getStock() +
      ", unidad='" + getUnidad() + "'" +
      ", tipo='" + getTipo() + "'" +
      ", imagen='" + getImagen() + "'" +
      ", precio=" + getPrecio() +
      ", empresa=" + getEmpresaId() +
      ", insumoRecomendado=" + getInsumoRecomendadoId() +
      ", insumoRecomendado='" + getInsumoRecomendadoNombre() + "'" +
      "}";
  }
}
