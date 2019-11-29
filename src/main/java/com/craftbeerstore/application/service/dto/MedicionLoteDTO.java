package com.craftbeerstore.application.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.craftbeerstore.application.domain.enumeration.TipoMedicion;

/**
 * A DTO for the {@link com.craftbeerstore.application.domain.MedicionLote} entity.
 */
public class MedicionLoteDTO implements Serializable {

    private Long id;

    private Integer dia;

    private TipoMedicion tipoMedicion;

    private String estado;

    private Instant fechaRealizado;

    private BigDecimal valor;

    private String observacion;


    private Long loteId;

    private String loteCodigo;

    private Long tanqueId;

    private String tanqueNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public TipoMedicion getTipoMedicion() {
        return tipoMedicion;
    }

    public void setTipoMedicion(TipoMedicion tipoMedicion) {
        this.tipoMedicion = tipoMedicion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Instant getFechaRealizado() {
        return fechaRealizado;
    }

    public void setFechaRealizado(Instant fechaRealizado) {
        this.fechaRealizado = fechaRealizado;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MedicionLoteDTO medicionLoteDTO = (MedicionLoteDTO) o;
        if (medicionLoteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicionLoteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicionLoteDTO{" +
            "id=" + getId() +
            ", dia=" + getDia() +
            ", tipoMedicion='" + getTipoMedicion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", fechaRealizado='" + getFechaRealizado() + "'" +
            ", valor=" + getValor() +
            ", observacion='" + getObservacion() + "'" +
            ", lote=" + getLoteId() +
            ", lote='" + getLoteCodigo() + "'" +
            ", tanque=" + getTanqueId() +
            ", tanque='" + getTanqueNombre() + "'" +
            "}";
    }
}
