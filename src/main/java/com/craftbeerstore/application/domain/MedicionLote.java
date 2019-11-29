package com.craftbeerstore.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import com.craftbeerstore.application.domain.enumeration.TipoMedicion;

/**
 * A MedicionLote.
 */
@Entity
@Table(name = "medicion_lote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MedicionLote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dia")
    private Integer dia;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_medicion")
    private TipoMedicion tipoMedicion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_realizado")
    private Instant fechaRealizado;

    @Column(name = "valor", precision = 21, scale = 2)
    private BigDecimal valor;

    @Column(name = "observacion")
    private String observacion;

    @ManyToOne
    @JsonIgnoreProperties("medicionLotes")
    private Lote lote;

    @ManyToOne
    @JsonIgnoreProperties("medicionLotes")
    private Tanque tanque;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDia() {
        return dia;
    }

    public MedicionLote dia(Integer dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public TipoMedicion getTipoMedicion() {
        return tipoMedicion;
    }

    public MedicionLote tipoMedicion(TipoMedicion tipoMedicion) {
        this.tipoMedicion = tipoMedicion;
        return this;
    }

    public void setTipoMedicion(TipoMedicion tipoMedicion) {
        this.tipoMedicion = tipoMedicion;
    }

    public String getEstado() {
        return estado;
    }

    public MedicionLote estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Instant getFechaRealizado() {
        return fechaRealizado;
    }

    public MedicionLote fechaRealizado(Instant fechaRealizado) {
        this.fechaRealizado = fechaRealizado;
        return this;
    }

    public void setFechaRealizado(Instant fechaRealizado) {
        this.fechaRealizado = fechaRealizado;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public MedicionLote valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacion() {
        return observacion;
    }

    public MedicionLote observacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Lote getLote() {
        return lote;
    }

    public MedicionLote lote(Lote lote) {
        this.lote = lote;
        return this;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Tanque getTanque() {
        return tanque;
    }

    public MedicionLote tanque(Tanque tanque) {
        this.tanque = tanque;
        return this;
    }

    public void setTanque(Tanque tanque) {
        this.tanque = tanque;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicionLote)) {
            return false;
        }
        return id != null && id.equals(((MedicionLote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MedicionLote{" +
            "id=" + getId() +
            ", dia=" + getDia() +
            ", tipoMedicion='" + getTipoMedicion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", fechaRealizado='" + getFechaRealizado() + "'" +
            ", valor=" + getValor() +
            ", observacion='" + getObservacion() + "'" +
            "}";
    }
}
