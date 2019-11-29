package com.craftbeerstore.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.craftbeerstore.application.domain.enumeration.EstadoCompra;

/**
 * A CompraInsumo.
 */
@Entity
@Table(name = "compra_insumo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompraInsumo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nro_factura")
    private String nroFactura;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "subtotal", precision = 21, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "gasto_de_envio", precision = 21, scale = 2)
    private BigDecimal gastoDeEnvio;

    @Column(name = "impuesto", precision = 21, scale = 2)
    private BigDecimal impuesto;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_compra")
    private EstadoCompra estadoCompra;

    @ManyToOne
    @JsonIgnoreProperties("compraInsumos")
    private Proveedor proveedor;

    @ManyToOne
    @JsonIgnoreProperties("compraInsumos")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNroFactura() {
        return nroFactura;
    }

    public CompraInsumo nroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
        return this;
    }

    public void setNroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public CompraInsumo fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public CompraInsumo subtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
        return this;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getGastoDeEnvio() {
        return gastoDeEnvio;
    }

    public CompraInsumo gastoDeEnvio(BigDecimal gastoDeEnvio) {
        this.gastoDeEnvio = gastoDeEnvio;
        return this;
    }

    public void setGastoDeEnvio(BigDecimal gastoDeEnvio) {
        this.gastoDeEnvio = gastoDeEnvio;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public CompraInsumo impuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
        return this;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public CompraInsumo total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public EstadoCompra getEstadoCompra() {
        return estadoCompra;
    }

    public CompraInsumo estadoCompra(EstadoCompra estadoCompra) {
        this.estadoCompra = estadoCompra;
        return this;
    }

    public void setEstadoCompra(EstadoCompra estadoCompra) {
        this.estadoCompra = estadoCompra;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public CompraInsumo proveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public CompraInsumo empresa(Empresa empresa) {
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
        if (!(o instanceof CompraInsumo)) {
            return false;
        }
        return id != null && id.equals(((CompraInsumo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompraInsumo{" +
            "id=" + getId() +
            ", nroFactura='" + getNroFactura() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", subtotal=" + getSubtotal() +
            ", gastoDeEnvio=" + getGastoDeEnvio() +
            ", impuesto=" + getImpuesto() +
            ", total=" + getTotal() +
            ", estadoCompra='" + getEstadoCompra() + "'" +
            "}";
    }
}
