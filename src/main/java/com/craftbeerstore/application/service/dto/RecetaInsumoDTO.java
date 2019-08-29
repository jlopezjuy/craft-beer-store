package com.craftbeerstore.application.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.craftbeerstore.application.domain.enumeration.TipoInsumo;
import com.craftbeerstore.application.domain.enumeration.UsoMalta;
import com.craftbeerstore.application.domain.enumeration.ModoLupulo;
import com.craftbeerstore.application.domain.enumeration.UsoLupulo;
import com.craftbeerstore.application.domain.enumeration.TipoOtro;
import com.craftbeerstore.application.domain.enumeration.UsoOtro;

/**
 * A DTO for the RecetaInsumo entity.
 */
public class RecetaInsumoDTO implements Serializable {

    private Long id;

    private TipoInsumo tipoInsumo;

    private BigDecimal cantidad;

    @Min(value = 2L)
    @Max(value = 40L)
    private Long color;

    @DecimalMax(value = "100")
    private BigDecimal porcentaje;

    private UsoMalta usoMalta;

    @Max(value = 100L)
    private Long alpha;

    private ModoLupulo modoLupulo;

    private BigDecimal gramos;

    private UsoLupulo usoLupulo;

    @Min(value = 0L)
    @Max(value = 120L)
    private Long tiempo;

    private BigDecimal ibu;

    private BigDecimal densidadLeva;

    private Long tamSobre;

    private Long atenuacion;

    private TipoOtro tipoOtro;

    private UsoOtro usoOtro;

    private Long tiempoOtro;


    private Long insumoRecomendadoId;

    private String insumoRecomendadoNombre;

    private Long recetaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoInsumo getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(TipoInsumo tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Long getColor() {
        return color;
    }

    public void setColor(Long color) {
        this.color = color;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public UsoMalta getUsoMalta() {
        return usoMalta;
    }

    public void setUsoMalta(UsoMalta usoMalta) {
        this.usoMalta = usoMalta;
    }

    public Long getAlpha() {
        return alpha;
    }

    public void setAlpha(Long alpha) {
        this.alpha = alpha;
    }

    public ModoLupulo getModoLupulo() {
        return modoLupulo;
    }

    public void setModoLupulo(ModoLupulo modoLupulo) {
        this.modoLupulo = modoLupulo;
    }

    public BigDecimal getGramos() {
        return gramos;
    }

    public void setGramos(BigDecimal gramos) {
        this.gramos = gramos;
    }

    public UsoLupulo getUsoLupulo() {
        return usoLupulo;
    }

    public void setUsoLupulo(UsoLupulo usoLupulo) {
        this.usoLupulo = usoLupulo;
    }

    public Long getTiempo() {
        return tiempo;
    }

    public void setTiempo(Long tiempo) {
        this.tiempo = tiempo;
    }

    public BigDecimal getIbu() {
        return ibu;
    }

    public void setIbu(BigDecimal ibu) {
        this.ibu = ibu;
    }

    public BigDecimal getDensidadLeva() {
        return densidadLeva;
    }

    public void setDensidadLeva(BigDecimal densidadLeva) {
        this.densidadLeva = densidadLeva;
    }

    public Long getTamSobre() {
        return tamSobre;
    }

    public void setTamSobre(Long tamSobre) {
        this.tamSobre = tamSobre;
    }

    public Long getAtenuacion() {
        return atenuacion;
    }

    public void setAtenuacion(Long atenuacion) {
        this.atenuacion = atenuacion;
    }

    public TipoOtro getTipoOtro() {
        return tipoOtro;
    }

    public void setTipoOtro(TipoOtro tipoOtro) {
        this.tipoOtro = tipoOtro;
    }

    public UsoOtro getUsoOtro() {
        return usoOtro;
    }

    public void setUsoOtro(UsoOtro usoOtro) {
        this.usoOtro = usoOtro;
    }

    public Long getTiempoOtro() {
        return tiempoOtro;
    }

    public void setTiempoOtro(Long tiempoOtro) {
        this.tiempoOtro = tiempoOtro;
    }

    public Long getInsumoRecomendadoId() {
        return insumoRecomendadoId;
    }

    public void setInsumoRecomendadoId(Long insumoRecomendadoId) {
        this.insumoRecomendadoId = insumoRecomendadoId;
    }

    public String getInsumoRecomendadoNombre() {
        return insumoRecomendadoNombre;
    }

    public void setInsumoRecomendadoNombre(String insumoRecomendadoNombre) {
        this.insumoRecomendadoNombre = insumoRecomendadoNombre;
    }

    public Long getRecetaId() {
        return recetaId;
    }

    public void setRecetaId(Long recetaId) {
        this.recetaId = recetaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecetaInsumoDTO recetaInsumoDTO = (RecetaInsumoDTO) o;
        if (recetaInsumoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recetaInsumoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecetaInsumoDTO{" +
            "id=" + getId() +
            ", tipoInsumo='" + getTipoInsumo() + "'" +
            ", cantidad=" + getCantidad() +
            ", color=" + getColor() +
            ", porcentaje=" + getPorcentaje() +
            ", usoMalta='" + getUsoMalta() + "'" +
            ", alpha=" + getAlpha() +
            ", modoLupulo='" + getModoLupulo() + "'" +
            ", gramos=" + getGramos() +
            ", usoLupulo='" + getUsoLupulo() + "'" +
            ", tiempo=" + getTiempo() +
            ", ibu=" + getIbu() +
            ", densidadLeva=" + getDensidadLeva() +
            ", tamSobre=" + getTamSobre() +
            ", atenuacion=" + getAtenuacion() +
            ", tipoOtro='" + getTipoOtro() + "'" +
            ", usoOtro='" + getUsoOtro() + "'" +
            ", tiempoOtro=" + getTiempoOtro() +
            ", insumoRecomendado=" + getInsumoRecomendadoId() +
            ", insumoRecomendado='" + getInsumoRecomendadoNombre() + "'" +
            ", receta=" + getRecetaId() +
            "}";
    }
}
