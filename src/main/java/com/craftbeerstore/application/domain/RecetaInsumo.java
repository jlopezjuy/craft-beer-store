package com.craftbeerstore.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.craftbeerstore.application.domain.enumeration.TipoInsumo;

/**
 * A RecetaInsumo.
 */
@Entity
@Table(name = "receta_insumo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "recetainsumo")
public class RecetaInsumo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_insumo")
    private TipoInsumo tipoInsumo;

    @Column(name = "cantidad", precision = 15, scale = 5)
    private BigDecimal cantidad;

    @ManyToOne
    @JsonIgnoreProperties("recetaInsumos")
    private Insumo insumo;

    @ManyToOne
    @JsonIgnoreProperties("recetaInsumos")
    private Receta receta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoInsumo getTipoInsumo() {
        return tipoInsumo;
    }

    public RecetaInsumo tipoInsumo(TipoInsumo tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
        return this;
    }

    public void setTipoInsumo(TipoInsumo tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public RecetaInsumo cantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public RecetaInsumo insumo(Insumo insumo) {
        this.insumo = insumo;
        return this;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public Receta getReceta() {
        return receta;
    }

    public RecetaInsumo receta(Receta receta) {
        this.receta = receta;
        return this;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecetaInsumo recetaInsumo = (RecetaInsumo) o;
        if (recetaInsumo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recetaInsumo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecetaInsumo{" +
            "id=" + getId() +
            ", tipoInsumo='" + getTipoInsumo() + "'" +
            ", cantidad=" + getCantidad() +
            "}";
    }
}
