package com.craftbeerstore.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A EventoProducto.
 */
@Entity
@Table(name = "evento_producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EventoProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad_de_barriles")
    private Long cantidadDeBarriles;

    @ManyToOne
    @JsonIgnoreProperties("eventoProductos")
    private Producto producto;

    @ManyToOne
    @JsonIgnoreProperties("eventoProductos")
    private Evento evento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCantidadDeBarriles() {
        return cantidadDeBarriles;
    }

    public EventoProducto cantidadDeBarriles(Long cantidadDeBarriles) {
        this.cantidadDeBarriles = cantidadDeBarriles;
        return this;
    }

    public void setCantidadDeBarriles(Long cantidadDeBarriles) {
        this.cantidadDeBarriles = cantidadDeBarriles;
    }

    public Producto getProducto() {
        return producto;
    }

    public EventoProducto producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Evento getEvento() {
        return evento;
    }

    public EventoProducto evento(Evento evento) {
        this.evento = evento;
        return this;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
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
        EventoProducto eventoProducto = (EventoProducto) o;
        if (eventoProducto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventoProducto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventoProducto{" +
            "id=" + getId() +
            ", cantidadDeBarriles=" + getCantidadDeBarriles() +
            "}";
    }
}
