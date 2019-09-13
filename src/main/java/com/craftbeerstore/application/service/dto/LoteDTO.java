package com.craftbeerstore.application.service.dto;

import com.craftbeerstore.application.domain.enumeration.EstadoLote;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the Lote entity.
 */
public class LoteDTO implements Serializable {

  private Long id;

  private String codigo;

  private LocalDate fechaCoccion;

  private Integer coccion;

  private String descripcion;

  private Boolean descuentaStock;

  private EstadoLote estado;

  private BigDecimal litrosEstimados;

  private BigDecimal litrosEnTanque;

  private BigDecimal litrosEnvasados;


  private Long recetaId;

  private String recetaNombre;

  private Long empresaId;

  private String empresaNombreEmpresa;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public LocalDate getFechaCoccion() {
    return fechaCoccion;
  }

  public void setFechaCoccion(LocalDate fechaCoccion) {
    this.fechaCoccion = fechaCoccion;
  }

  public Integer getCoccion() {
    return coccion;
  }

  public void setCoccion(Integer coccion) {
    this.coccion = coccion;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Boolean isDescuentaStock() {
    return descuentaStock;
  }

  public void setDescuentaStock(Boolean descuentaStock) {
    this.descuentaStock = descuentaStock;
  }

  public EstadoLote getEstado() {
    return estado;
  }

  public void setEstado(EstadoLote estado) {
    this.estado = estado;
  }

  public BigDecimal getLitrosEstimados() {
    return litrosEstimados;
  }

  public void setLitrosEstimados(BigDecimal litrosEstimados) {
    this.litrosEstimados = litrosEstimados;
  }

  public BigDecimal getLitrosEnTanque() {
    return litrosEnTanque;
  }

  public void setLitrosEnTanque(BigDecimal litrosEnTanque) {
    this.litrosEnTanque = litrosEnTanque;
  }

  public BigDecimal getLitrosEnvasados() {
    return litrosEnvasados;
  }

  public void setLitrosEnvasados(BigDecimal litrosEnvasados) {
    this.litrosEnvasados = litrosEnvasados;
  }

  public Long getRecetaId() {
    return recetaId;
  }

  public void setRecetaId(Long recetaId) {
    this.recetaId = recetaId;
  }

  public String getRecetaNombre() {
    return recetaNombre;
  }

  public void setRecetaNombre(String recetaNombre) {
    this.recetaNombre = recetaNombre;
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

    LoteDTO loteDTO = (LoteDTO) o;
    if (loteDTO.getId() == null || getId() == null) {
      return false;
    }
    return Objects.equals(getId(), loteDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "LoteDTO{" +
      "id=" + getId() +
      ", codigo='" + getCodigo() + "'" +
      ", fechaCoccion='" + getFechaCoccion() + "'" +
      ", coccion=" + getCoccion() +
      ", descripcion='" + getDescripcion() + "'" +
      ", descuentaStock='" + isDescuentaStock() + "'" +
      ", estado='" + getEstado() + "'" +
      ", litrosEstimados=" + getLitrosEstimados() +
      ", litrosEnTanque=" + getLitrosEnTanque() +
      ", litrosEnvasados=" + getLitrosEnvasados() +
      ", receta=" + getRecetaId() +
      ", receta='" + getRecetaNombre() + "'" +
      ", empresa=" + getEmpresaId() +
      ", empresa='" + getEmpresaNombreEmpresa() + "'" +
      "}";
  }
}
