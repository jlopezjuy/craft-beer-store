package com.craftbeerstore.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.craftbeerstore.application.domain.enumeration.TipoInsumo;

/**
 * A InsumoRecomendado.
 */
@Entity
@Table(name = "insumo_recomendado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InsumoRecomendado implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "marca")
    private String marca;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoInsumo tipo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public InsumoRecomendado nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public InsumoRecomendado marca(String marca) {
        this.marca = marca;
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public TipoInsumo getTipo() {
        return tipo;
    }

    public InsumoRecomendado tipo(TipoInsumo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoInsumo tipo) {
        this.tipo = tipo;
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
        InsumoRecomendado insumoRecomendado = (InsumoRecomendado) o;
        if (insumoRecomendado.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insumoRecomendado.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InsumoRecomendado{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", marca='" + getMarca() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
