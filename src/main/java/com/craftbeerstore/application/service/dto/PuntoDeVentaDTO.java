package com.craftbeerstore.application.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the PuntoDeVenta entity.
 */
public class PuntoDeVentaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String direccionDeEntrega;

    @NotNull
    private String localidad;

    @Lob
    private String notas;


    private Long clienteId;

    private String clienteNombreApellido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccionDeEntrega() {
        return direccionDeEntrega;
    }

    public void setDireccionDeEntrega(String direccionDeEntrega) {
        this.direccionDeEntrega = direccionDeEntrega;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombreApellido() {
        return clienteNombreApellido;
    }

    public void setClienteNombreApellido(String clienteNombreApellido) {
        this.clienteNombreApellido = clienteNombreApellido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PuntoDeVentaDTO puntoDeVentaDTO = (PuntoDeVentaDTO) o;
        if (puntoDeVentaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), puntoDeVentaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PuntoDeVentaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", direccionDeEntrega='" + getDireccionDeEntrega() + "'" +
            ", localidad='" + getLocalidad() + "'" +
            ", notas='" + getNotas() + "'" +
            ", cliente=" + getClienteId() +
            ", cliente='" + getClienteNombreApellido() + "'" +
            "}";
    }
}
