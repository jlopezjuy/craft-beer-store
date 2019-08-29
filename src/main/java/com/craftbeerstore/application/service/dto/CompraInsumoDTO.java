package com.craftbeerstore.application.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.craftbeerstore.application.domain.enumeration.EstadoCompra;

/**
 * A DTO for the CompraInsumo entity.
 */
public class CompraInsumoDTO implements Serializable {

    private Long id;

    private String nroFactura;

    private LocalDate fecha;

    private BigDecimal subtotal;

    private BigDecimal gastoDeEnvio;

    private BigDecimal impuesto;

    private BigDecimal total;

    private EstadoCompra estadoCompra;


    private Long proveedorId;

    private String proveedorNombreProveedor;

    private Long empresaId;

    private String empresaNombreEmpresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getGastoDeEnvio() {
        return gastoDeEnvio;
    }

    public void setGastoDeEnvio(BigDecimal gastoDeEnvio) {
        this.gastoDeEnvio = gastoDeEnvio;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(BigDecimal impuesto) {
        this.impuesto = impuesto;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public EstadoCompra getEstadoCompra() {
        return estadoCompra;
    }

    public void setEstadoCompra(EstadoCompra estadoCompra) {
        this.estadoCompra = estadoCompra;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getProveedorNombreProveedor() {
        return proveedorNombreProveedor;
    }

    public void setProveedorNombreProveedor(String proveedorNombreProveedor) {
        this.proveedorNombreProveedor = proveedorNombreProveedor;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public String getEmpresaNombreEmpresa() {
        return empresaNombreEmpresa;
    }

    public void setEmpresaNombreEmpresa(String empresaNombreEmpresa) {
        this.empresaNombreEmpresa = empresaNombreEmpresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompraInsumoDTO compraInsumoDTO = (CompraInsumoDTO) o;
        if (compraInsumoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compraInsumoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompraInsumoDTO{" +
            "id=" + getId() +
            ", nroFactura='" + getNroFactura() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", subtotal=" + getSubtotal() +
            ", gastoDeEnvio=" + getGastoDeEnvio() +
            ", impuesto=" + getImpuesto() +
            ", total=" + getTotal() +
            ", estadoCompra='" + getEstadoCompra() + "'" +
            ", proveedor=" + getProveedorId() +
            ", proveedor='" + getProveedorNombreProveedor() + "'" +
            ", empresa=" + getEmpresaId() +
            ", empresa='" + getEmpresaNombreEmpresa() + "'" +
            "}";
    }
}
