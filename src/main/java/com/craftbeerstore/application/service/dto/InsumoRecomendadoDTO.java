package com.craftbeerstore.application.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the InsumoRecomendado entity.
 */
public class InsumoRecomendadoDTO implements Serializable {

    private Long id;

    private String nombre;

    private String marca;


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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InsumoRecomendadoDTO insumoRecomendadoDTO = (InsumoRecomendadoDTO) o;
        if (insumoRecomendadoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insumoRecomendadoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InsumoRecomendadoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", marca='" + getMarca() + "'" +
            "}";
    }
}
