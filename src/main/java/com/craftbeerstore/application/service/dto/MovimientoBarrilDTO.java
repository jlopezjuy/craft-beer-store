package com.craftbeerstore.application.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import com.craftbeerstore.application.domain.enumeration.EstadoMovimientoBarril;

/**
 * A DTO for the {@link com.craftbeerstore.application.domain.MovimientoBarril} entity.
 */
public class MovimientoBarrilDTO implements Serializable {

    private Long id;

    private LocalDate fechaMovimiento;

    private EstadoMovimientoBarril estado;

    private Long dias;


    private Long barrilId;

    private String barrilCodigo;

    private Long productoId;

    private String productoNombreComercial;

    private Long loteId;

    private String loteCodigo;

    private Long clienteId;

    private String clienteNombreApellido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(LocalDate fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public EstadoMovimientoBarril getEstado() {
        return estado;
    }

    public void setEstado(EstadoMovimientoBarril estado) {
        this.estado = estado;
    }

    public Long getDias() {
        return dias;
    }

    public void setDias(Long dias) {
        this.dias = dias;
    }

    public Long getBarrilId() {
        return barrilId;
    }

    public void setBarrilId(Long barrilId) {
        this.barrilId = barrilId;
    }

    public String getBarrilCodigo() {
        return barrilCodigo;
    }

    public void setBarrilCodigo(String barrilCodigo) {
        this.barrilCodigo = barrilCodigo;
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

        MovimientoBarrilDTO movimientoBarrilDTO = (MovimientoBarrilDTO) o;
        if (movimientoBarrilDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movimientoBarrilDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovimientoBarrilDTO{" +
            "id=" + getId() +
            ", fechaMovimiento='" + getFechaMovimiento() + "'" +
            ", estado='" + getEstado() + "'" +
            ", dias=" + getDias() +
            ", barril=" + getBarrilId() +
            ", barril='" + getBarrilCodigo() + "'" +
            ", producto=" + getProductoId() +
            ", producto='" + getProductoNombreComercial() + "'" +
            ", lote=" + getLoteId() +
            ", lote='" + getLoteCodigo() + "'" +
            ", cliente=" + getClienteId() +
            ", cliente='" + getClienteNombreApellido() + "'" +
            "}";
    }
}
