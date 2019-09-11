package com.craftbeerstore.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.craftbeerstore.application.domain.enumeration.EstadoLote;

/**
 * A Lote.
 */
@Entity
@Table(name = "lote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lote implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "fecha_coccion")
    private LocalDate fechaCoccion;

    @Column(name = "coccion")
    private Integer coccion;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "descuenta_stock")
    private Boolean descuentaStock;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoLote estado;

    @Column(name = "litros_estimados", precision = 10, scale = 2)
    private BigDecimal litrosEstimados;

    @Column(name = "litros_en_tanque", precision = 10, scale = 2)
    private BigDecimal litrosEnTanque;

    @Column(name = "litros_envasados", precision = 10, scale = 2)
    private BigDecimal litrosEnvasados;

    @ManyToOne
    @JsonIgnoreProperties("lotes")
    private Receta receta;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Lote codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFechaCoccion() {
        return fechaCoccion;
    }

    public Lote fechaCoccion(LocalDate fechaCoccion) {
        this.fechaCoccion = fechaCoccion;
        return this;
    }

    public void setFechaCoccion(LocalDate fechaCoccion) {
        this.fechaCoccion = fechaCoccion;
    }

    public Integer getCoccion() {
        return coccion;
    }

    public Lote coccion(Integer coccion) {
        this.coccion = coccion;
        return this;
    }

    public void setCoccion(Integer coccion) {
        this.coccion = coccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Lote descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean isDescuentaStock() {
        return descuentaStock;
    }

    public Lote descuentaStock(Boolean descuentaStock) {
        this.descuentaStock = descuentaStock;
        return this;
    }

    public void setDescuentaStock(Boolean descuentaStock) {
        this.descuentaStock = descuentaStock;
    }

    public EstadoLote getEstado() {
        return estado;
    }

    public Lote estado(EstadoLote estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoLote estado) {
        this.estado = estado;
    }

    public BigDecimal getLitrosEstimados() {
        return litrosEstimados;
    }

    public Lote litrosEstimados(BigDecimal litrosEstimados) {
        this.litrosEstimados = litrosEstimados;
        return this;
    }

    public void setLitrosEstimados(BigDecimal litrosEstimados) {
        this.litrosEstimados = litrosEstimados;
    }

    public BigDecimal getLitrosEnTanque() {
        return litrosEnTanque;
    }

    public Lote litrosEnTanque(BigDecimal litrosEnTanque) {
        this.litrosEnTanque = litrosEnTanque;
        return this;
    }

    public void setLitrosEnTanque(BigDecimal litrosEnTanque) {
        this.litrosEnTanque = litrosEnTanque;
    }

    public BigDecimal getLitrosEnvasados() {
        return litrosEnvasados;
    }

    public Lote litrosEnvasados(BigDecimal litrosEnvasados) {
        this.litrosEnvasados = litrosEnvasados;
        return this;
    }

    public void setLitrosEnvasados(BigDecimal litrosEnvasados) {
        this.litrosEnvasados = litrosEnvasados;
    }

    public Receta getReceta() {
        return receta;
    }

    public Lote receta(Receta receta) {
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lote lote = (Lote) o;
        if (lote.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lote.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lote{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", fechaCoccion='" + getFechaCoccion() + "'" +
            ", coccion=" + getCoccion() +
            ", descripcion='" + getDescripcion() + "'" +
            ", descuentaStock='" + isDescuentaStock() + "'" +
            ", estado='" + getEstado() + "'" +
            ", litrosEstimados=" + getLitrosEstimados() +
            ", litrosEnTanque=" + getLitrosEnTanque() +
            ", litrosEnvasados=" + getLitrosEnvasados() +
            "}";
    }
}
