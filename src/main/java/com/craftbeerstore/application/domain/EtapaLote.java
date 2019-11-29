package com.craftbeerstore.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.craftbeerstore.application.domain.enumeration.EtapaLoteEnum;

/**
 * A EtapaLote.
 */
@Entity
@Table(name = "etapa_lote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EtapaLote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "etapa")
    private EtapaLoteEnum etapa;

    @Column(name = "litros", precision = 21, scale = 2)
    private BigDecimal litros;

    @Column(name = "inicio")
    private LocalDate inicio;

    @Column(name = "fin")
    private LocalDate fin;

    @Column(name = "dias")
    private Integer dias;

    @ManyToOne
    @JsonIgnoreProperties("etapaLotes")
    private Tanque tanque;

    @ManyToOne
    @JsonIgnoreProperties("etapaLotes")
    private Lote lote;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtapaLoteEnum getEtapa() {
        return etapa;
    }

    public EtapaLote etapa(EtapaLoteEnum etapa) {
        this.etapa = etapa;
        return this;
    }

    public void setEtapa(EtapaLoteEnum etapa) {
        this.etapa = etapa;
    }

    public BigDecimal getLitros() {
        return litros;
    }

    public EtapaLote litros(BigDecimal litros) {
        this.litros = litros;
        return this;
    }

    public void setLitros(BigDecimal litros) {
        this.litros = litros;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public EtapaLote inicio(LocalDate inicio) {
        this.inicio = inicio;
        return this;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public EtapaLote fin(LocalDate fin) {
        this.fin = fin;
        return this;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public Integer getDias() {
        return dias;
    }

    public EtapaLote dias(Integer dias) {
        this.dias = dias;
        return this;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Tanque getTanque() {
        return tanque;
    }

    public EtapaLote tanque(Tanque tanque) {
        this.tanque = tanque;
        return this;
    }

    public void setTanque(Tanque tanque) {
        this.tanque = tanque;
    }

    public Lote getLote() {
        return lote;
    }

    public EtapaLote lote(Lote lote) {
        this.lote = lote;
        return this;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtapaLote)) {
            return false;
        }
        return id != null && id.equals(((EtapaLote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EtapaLote{" +
            "id=" + getId() +
            ", etapa='" + getEtapa() + "'" +
            ", litros=" + getLitros() +
            ", inicio='" + getInicio() + "'" +
            ", fin='" + getFin() + "'" +
            ", dias=" + getDias() +
            "}";
    }
}
