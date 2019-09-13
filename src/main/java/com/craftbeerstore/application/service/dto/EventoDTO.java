package com.craftbeerstore.application.service.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the Evento entity.
 */
public class EventoDTO implements Serializable {

  private Long id;

  @NotNull
  private String nombreEvento;

  @NotNull
  private LocalDate fechaEvento;

  @NotNull
  @Min(value = 1L)
  private Long cantidadBarriles;

  @NotNull
  private BigDecimal precioPinta;


  private Long empresaId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombreEvento() {
    return nombreEvento;
  }

  public void setNombreEvento(String nombreEvento) {
    this.nombreEvento = nombreEvento;
  }

  public LocalDate getFechaEvento() {
    return fechaEvento;
  }

  public void setFechaEvento(LocalDate fechaEvento) {
    this.fechaEvento = fechaEvento;
  }

  public Long getCantidadBarriles() {
    return cantidadBarriles;
  }

  public void setCantidadBarriles(Long cantidadBarriles) {
    this.cantidadBarriles = cantidadBarriles;
  }

  public BigDecimal getPrecioPinta() {
    return precioPinta;
  }

  public void setPrecioPinta(BigDecimal precioPinta) {
    this.precioPinta = precioPinta;
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

    EventoDTO eventoDTO = (EventoDTO) o;
    if (eventoDTO.getId() == null || getId() == null) {
      return false;
    }
    return Objects.equals(getId(), eventoDTO.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "EventoDTO{" +
      "id=" + getId() +
      ", nombreEvento='" + getNombreEvento() + "'" +
      ", fechaEvento='" + getFechaEvento() + "'" +
      ", cantidadBarriles=" + getCantidadBarriles() +
      ", precioPinta=" + getPrecioPinta() +
      ", empresa=" + getEmpresaId() +
      "}";
  }
}
