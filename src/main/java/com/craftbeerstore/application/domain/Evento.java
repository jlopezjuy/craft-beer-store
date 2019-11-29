package com.craftbeerstore.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Evento.
 */
@Entity
@Table(name = "evento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre_evento", nullable = false)
    private String nombreEvento;

    @NotNull
    @Column(name = "fecha_evento", nullable = false)
    private LocalDate fechaEvento;

    @NotNull
    @Min(value = 1L)
    @Column(name = "cantidad_barriles", nullable = false)
    private Long cantidadBarriles;

    @NotNull
    @Column(name = "precio_pinta", precision = 21, scale = 2, nullable = false)
    private BigDecimal precioPinta;

    @ManyToOne
    @JsonIgnoreProperties("eventos")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public Evento nombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
        return this;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public Evento fechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
        return this;
    }

    public void setFechaEvento(LocalDate fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public Long getCantidadBarriles() {
        return cantidadBarriles;
    }

    public Evento cantidadBarriles(Long cantidadBarriles) {
        this.cantidadBarriles = cantidadBarriles;
        return this;
    }

    public void setCantidadBarriles(Long cantidadBarriles) {
        this.cantidadBarriles = cantidadBarriles;
    }

    public BigDecimal getPrecioPinta() {
        return precioPinta;
    }

    public Evento precioPinta(BigDecimal precioPinta) {
        this.precioPinta = precioPinta;
        return this;
    }

    public void setPrecioPinta(BigDecimal precioPinta) {
        this.precioPinta = precioPinta;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Evento empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Evento)) {
            return false;
        }
        return id != null && id.equals(((Evento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Evento{" +
            "id=" + getId() +
            ", nombreEvento='" + getNombreEvento() + "'" +
            ", fechaEvento='" + getFechaEvento() + "'" +
            ", cantidadBarriles=" + getCantidadBarriles() +
            ", precioPinta=" + getPrecioPinta() +
            "}";
    }
}
