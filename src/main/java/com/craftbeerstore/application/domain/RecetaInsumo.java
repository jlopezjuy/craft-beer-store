package com.craftbeerstore.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

import com.craftbeerstore.application.domain.enumeration.TipoInsumo;

import com.craftbeerstore.application.domain.enumeration.UsoMalta;

import com.craftbeerstore.application.domain.enumeration.ModoLupulo;

import com.craftbeerstore.application.domain.enumeration.UsoLupulo;

import com.craftbeerstore.application.domain.enumeration.TipoOtro;

import com.craftbeerstore.application.domain.enumeration.UsoOtro;

/**
 * A RecetaInsumo.
 */
@Entity
@Table(name = "receta_insumo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RecetaInsumo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_insumo")
    private TipoInsumo tipoInsumo;

    @Column(name = "cantidad", precision = 21, scale = 2)
    private BigDecimal cantidad;

    @Min(value = 2L)
    @Max(value = 40L)
    @Column(name = "color")
    private Long color;

    @DecimalMax(value = "100")
    @Column(name = "porcentaje", precision = 21, scale = 2)
    private BigDecimal porcentaje;

    @Enumerated(EnumType.STRING)
    @Column(name = "uso_malta")
    private UsoMalta usoMalta;

    @Max(value = 100L)
    @Column(name = "alpha")
    private Long alpha;

    @Enumerated(EnumType.STRING)
    @Column(name = "modo_lupulo")
    private ModoLupulo modoLupulo;

    @Column(name = "gramos", precision = 21, scale = 2)
    private BigDecimal gramos;

    @Enumerated(EnumType.STRING)
    @Column(name = "uso_lupulo")
    private UsoLupulo usoLupulo;

    @Min(value = 0L)
    @Max(value = 120L)
    @Column(name = "tiempo")
    private Long tiempo;

    @Column(name = "ibu", precision = 21, scale = 2)
    private BigDecimal ibu;

    @Column(name = "densidad_leva", precision = 21, scale = 2)
    private BigDecimal densidadLeva;

    @Column(name = "tam_sobre")
    private Long tamSobre;

    @Column(name = "atenuacion")
    private Long atenuacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_otro")
    private TipoOtro tipoOtro;

    @Enumerated(EnumType.STRING)
    @Column(name = "uso_otro")
    private UsoOtro usoOtro;

    @Column(name = "tiempo_otro")
    private Long tiempoOtro;

    @ManyToOne
    @JsonIgnoreProperties("recetaInsumos")
    private InsumoRecomendado insumoRecomendado;

    @ManyToOne
    @JsonIgnoreProperties("recetaInsumos")
    private Receta receta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoInsumo getTipoInsumo() {
        return tipoInsumo;
    }

    public RecetaInsumo tipoInsumo(TipoInsumo tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
        return this;
    }

    public void setTipoInsumo(TipoInsumo tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public RecetaInsumo cantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Long getColor() {
        return color;
    }

    public RecetaInsumo color(Long color) {
        this.color = color;
        return this;
    }

    public void setColor(Long color) {
        this.color = color;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public RecetaInsumo porcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
        return this;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public UsoMalta getUsoMalta() {
        return usoMalta;
    }

    public RecetaInsumo usoMalta(UsoMalta usoMalta) {
        this.usoMalta = usoMalta;
        return this;
    }

    public void setUsoMalta(UsoMalta usoMalta) {
        this.usoMalta = usoMalta;
    }

    public Long getAlpha() {
        return alpha;
    }

    public RecetaInsumo alpha(Long alpha) {
        this.alpha = alpha;
        return this;
    }

    public void setAlpha(Long alpha) {
        this.alpha = alpha;
    }

    public ModoLupulo getModoLupulo() {
        return modoLupulo;
    }

    public RecetaInsumo modoLupulo(ModoLupulo modoLupulo) {
        this.modoLupulo = modoLupulo;
        return this;
    }

    public void setModoLupulo(ModoLupulo modoLupulo) {
        this.modoLupulo = modoLupulo;
    }

    public BigDecimal getGramos() {
        return gramos;
    }

    public RecetaInsumo gramos(BigDecimal gramos) {
        this.gramos = gramos;
        return this;
    }

    public void setGramos(BigDecimal gramos) {
        this.gramos = gramos;
    }

    public UsoLupulo getUsoLupulo() {
        return usoLupulo;
    }

    public RecetaInsumo usoLupulo(UsoLupulo usoLupulo) {
        this.usoLupulo = usoLupulo;
        return this;
    }

    public void setUsoLupulo(UsoLupulo usoLupulo) {
        this.usoLupulo = usoLupulo;
    }

    public Long getTiempo() {
        return tiempo;
    }

    public RecetaInsumo tiempo(Long tiempo) {
        this.tiempo = tiempo;
        return this;
    }

    public void setTiempo(Long tiempo) {
        this.tiempo = tiempo;
    }

    public BigDecimal getIbu() {
        return ibu;
    }

    public RecetaInsumo ibu(BigDecimal ibu) {
        this.ibu = ibu;
        return this;
    }

    public void setIbu(BigDecimal ibu) {
        this.ibu = ibu;
    }

    public BigDecimal getDensidadLeva() {
        return densidadLeva;
    }

    public RecetaInsumo densidadLeva(BigDecimal densidadLeva) {
        this.densidadLeva = densidadLeva;
        return this;
    }

    public void setDensidadLeva(BigDecimal densidadLeva) {
        this.densidadLeva = densidadLeva;
    }

    public Long getTamSobre() {
        return tamSobre;
    }

    public RecetaInsumo tamSobre(Long tamSobre) {
        this.tamSobre = tamSobre;
        return this;
    }

    public void setTamSobre(Long tamSobre) {
        this.tamSobre = tamSobre;
    }

    public Long getAtenuacion() {
        return atenuacion;
    }

    public RecetaInsumo atenuacion(Long atenuacion) {
        this.atenuacion = atenuacion;
        return this;
    }

    public void setAtenuacion(Long atenuacion) {
        this.atenuacion = atenuacion;
    }

    public TipoOtro getTipoOtro() {
        return tipoOtro;
    }

    public RecetaInsumo tipoOtro(TipoOtro tipoOtro) {
        this.tipoOtro = tipoOtro;
        return this;
    }

    public void setTipoOtro(TipoOtro tipoOtro) {
        this.tipoOtro = tipoOtro;
    }

    public UsoOtro getUsoOtro() {
        return usoOtro;
    }

    public RecetaInsumo usoOtro(UsoOtro usoOtro) {
        this.usoOtro = usoOtro;
        return this;
    }

    public void setUsoOtro(UsoOtro usoOtro) {
        this.usoOtro = usoOtro;
    }

    public Long getTiempoOtro() {
        return tiempoOtro;
    }

    public RecetaInsumo tiempoOtro(Long tiempoOtro) {
        this.tiempoOtro = tiempoOtro;
        return this;
    }

    public void setTiempoOtro(Long tiempoOtro) {
        this.tiempoOtro = tiempoOtro;
    }

    public InsumoRecomendado getInsumoRecomendado() {
        return insumoRecomendado;
    }

    public RecetaInsumo insumoRecomendado(InsumoRecomendado insumoRecomendado) {
        this.insumoRecomendado = insumoRecomendado;
        return this;
    }

    public void setInsumoRecomendado(InsumoRecomendado insumoRecomendado) {
        this.insumoRecomendado = insumoRecomendado;
    }

    public Receta getReceta() {
        return receta;
    }

    public RecetaInsumo receta(Receta receta) {
        this.receta = receta;
        return this;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecetaInsumo)) {
            return false;
        }
        return id != null && id.equals(((RecetaInsumo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RecetaInsumo{" +
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
            "}";
    }
}
