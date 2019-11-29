package com.craftbeerstore.application.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import com.craftbeerstore.application.domain.enumeration.EstadoUsoTanque;

/**
 * A DTO for the {@link com.craftbeerstore.application.domain.MovimientoTanque} entity.
 */
public class MovimientoTanqueDTO implements Serializable {

    private Long id;

    private LocalDate fecha;

    private EstadoUsoTanque estado;

    private Integer dias;


    private Long tanqueId;

    private String tanqueNombre;

    private Long productoId;

    private String productoNombreComercial;

    private Long loteId;

    private String loteCodigo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public EstadoUsoTanque getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsoTanque estado) {
        this.estado = estado;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Long getTanqueId() {
        return tanqueId;
    }

    public void setTanqueId(Long tanqueId) {
        this.tanqueId = tanqueId;
    }

    public String getTanqueNombre() {
        return tanqueNombre;
    }

    public void setTanqueNombre(String tanqueNombre) {
        this.tanqueNombre = tanqueNombre;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getProductoNombreComercial() {
        return productoNombreComercial;
    }

    public void setProductoNombreComercial(String productoNombreComercial) {
        this.productoNombreComercial = productoNombreComercial;
    }

    public Long getLoteId() {
        return loteId;
    }

    public void setLoteId(Long loteId) {
        this.loteId = loteId;
    }

    public String getLoteCodigo() {
        return loteCodigo;
    }

    public void setLoteCodigo(String loteCodigo) {
        this.loteCodigo = loteCodigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MovimientoTanqueDTO movimientoTanqueDTO = (MovimientoTanqueDTO) o;
        if (movimientoTanqueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movimientoTanqueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovimientoTanqueDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", estado='" + getEstado() + "'" +
            ", dias=" + getDias() +
            ", tanque=" + getTanqueId() +
            ", tanque='" + getTanqueNombre() + "'" +
            ", producto=" + getProductoId() +
            ", producto='" + getProductoNombreComercial() + "'" +
            ", lote=" + getLoteId() +
            ", lote='" + getLoteCodigo() + "'" +
            "}";
    }
}
