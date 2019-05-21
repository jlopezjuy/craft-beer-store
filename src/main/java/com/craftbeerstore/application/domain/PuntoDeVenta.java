package com.craftbeerstore.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PuntoDeVenta.
 */
@Entity
@Table(name = "punto_de_venta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "puntodeventa")
public class PuntoDeVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "direccion_de_entrega", nullable = false)
    private String direccionDeEntrega;

    @NotNull
    @Column(name = "localidad", nullable = false)
    private String localidad;

    @Lob
    @Column(name = "notas")
    private String notas;

    @ManyToOne
    private Cliente cliente;

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

    public PuntoDeVenta nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccionDeEntrega() {
        return direccionDeEntrega;
    }

    public PuntoDeVenta direccionDeEntrega(String direccionDeEntrega) {
        this.direccionDeEntrega = direccionDeEntrega;
        return this;
    }

    public void setDireccionDeEntrega(String direccionDeEntrega) {
        this.direccionDeEntrega = direccionDeEntrega;
    }

    public String getLocalidad() {
        return localidad;
    }

    public PuntoDeVenta localidad(String localidad) {
        this.localidad = localidad;
        return this;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getNotas() {
        return notas;
    }

    public PuntoDeVenta notas(String notas) {
        this.notas = notas;
        return this;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public PuntoDeVenta cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
        PuntoDeVenta puntoDeVenta = (PuntoDeVenta) o;
        if (puntoDeVenta.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), puntoDeVenta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PuntoDeVenta{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", direccionDeEntrega='" + getDireccionDeEntrega() + "'" +
            ", localidad='" + getLocalidad() + "'" +
            ", notas='" + getNotas() + "'" +
            "}";
    }
}
