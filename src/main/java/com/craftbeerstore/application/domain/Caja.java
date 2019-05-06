package com.craftbeerstore.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.craftbeerstore.application.domain.enumeration.TipoMovimientoCaja;

import com.craftbeerstore.application.domain.enumeration.TipoPago;

/**
 * A Caja.
 */
@Entity
@Table(name = "caja")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "caja")
public class Caja implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false)
    private TipoMovimientoCaja tipoMovimiento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pago", nullable = false)
    private TipoPago tipoPago;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "saldo_cta_cte", precision = 10, scale = 2)
    private BigDecimal saldoCtaCte;

    @NotNull
    @Column(name = "importe", precision = 10, scale = 2, nullable = false)
    private BigDecimal importe;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JsonIgnoreProperties("cajas")
    private Proveedor proveedor;

    @ManyToOne
    @JsonIgnoreProperties("cajas")
    private Cliente cliente;

    @ManyToOne
    @JsonIgnoreProperties("cajas")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoMovimientoCaja getTipoMovimiento() {
        return tipoMovimiento;
    }

    public Caja tipoMovimiento(TipoMovimientoCaja tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
        return this;
    }

    public void setTipoMovimiento(TipoMovimientoCaja tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public Caja tipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
        return this;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Caja descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getSaldoCtaCte() {
        return saldoCtaCte;
    }

    public Caja saldoCtaCte(BigDecimal saldoCtaCte) {
        this.saldoCtaCte = saldoCtaCte;
        return this;
    }

    public void setSaldoCtaCte(BigDecimal saldoCtaCte) {
        this.saldoCtaCte = saldoCtaCte;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public Caja importe(BigDecimal importe) {
        this.importe = importe;
        return this;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Caja fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Caja proveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Caja cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Caja empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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
        Caja caja = (Caja) o;
        if (caja.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), caja.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Caja{" +
            "id=" + getId() +
            ", tipoMovimiento='" + getTipoMovimiento() + "'" +
            ", tipoPago='" + getTipoPago() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", saldoCtaCte=" + getSaldoCtaCte() +
            ", importe=" + getImporte() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
