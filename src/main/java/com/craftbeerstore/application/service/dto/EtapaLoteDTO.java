package com.craftbeerstore.application.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.craftbeerstore.application.domain.enumeration.EtapaLoteEnum;

/**
 * A DTO for the EtapaLote entity.
 */
public class EtapaLoteDTO implements Serializable {

    private Long id;

    private EtapaLoteEnum etapa;

    private BigDecimal litros;

    private LocalDate inicio;

    private LocalDate fin;

    private Integer dias;


    private Long tanqueId;

    private String tanqueNombre;

    private Long loteId;

    private String loteCodigo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtapaLoteEnum getEtapa() {
        return etapa;
    }

    public void setEtapa(EtapaLoteEnum etapa) {
        this.etapa = etapa;
    }

    public BigDecimal getLitros() {
        return litros;
    }

    public void setLitros(BigDecimal litros) {
        this.litros = litros;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
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

        EtapaLoteDTO etapaLoteDTO = (EtapaLoteDTO) o;
        if (etapaLoteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etapaLoteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EtapaLoteDTO{" +
            "id=" + getId() +
            ", etapa='" + getEtapa() + "'" +
            ", litros=" + getLitros() +
            ", inicio='" + getInicio() + "'" +
            ", fin='" + getFin() + "'" +
            ", dias=" + getDias() +
            ", tanque=" + getTanqueId() +
            ", tanque='" + getTanqueNombre() + "'" +
            ", lote=" + getLoteId() +
            ", lote='" + getLoteCodigo() + "'" +
            "}";
    }
}
