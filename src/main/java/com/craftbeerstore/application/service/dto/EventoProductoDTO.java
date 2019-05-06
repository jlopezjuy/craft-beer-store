package com.craftbeerstore.application.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EventoProducto entity.
 */
public class EventoProductoDTO implements Serializable {

    private Long id;


    private Long productoId;

    private String productoNombreProducto;

    private Long eventoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getProductoNombreProducto() {
        return productoNombreProducto;
    }

    public void setProductoNombreProducto(String productoNombreProducto) {
        this.productoNombreProducto = productoNombreProducto;
    }

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventoProductoDTO eventoProductoDTO = (EventoProductoDTO) o;
        if (eventoProductoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventoProductoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventoProductoDTO{" +
            "id=" + getId() +
            ", producto=" + getProductoId() +
            ", producto='" + getProductoNombreProducto() + "'" +
            ", evento=" + getEventoId() +
            "}";
    }
}
