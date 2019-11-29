package com.craftbeerstore.application.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.craftbeerstore.application.domain.enumeration.TipoTanque;
import com.craftbeerstore.application.domain.enumeration.EstadoTanque;

/**
 * A DTO for the {@link com.craftbeerstore.application.domain.Tanque} entity.
 */
public class TanqueDTO implements Serializable {

    private Long id;

    private String nombre;

    private BigDecimal litros;

    private TipoTanque tipo;

    private EstadoTanque estado;

    private BigDecimal listrosDisponible;

    private LocalDate fechaIngreso;


    private Long loteId;

    private String loteCodigo;

    private Long productoId;

    private String productoNombreComercial;

    private Long empresaId;

    private String empresaNombreEmpresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getLitros() {
        return litros;
    }

    public void setLitros(BigDecimal litros) {
        this.litros = litros;
    }

    public TipoTanque getTipo() {
        return tipo;
    }

    public void setTipo(TipoTanque tipo) {
        this.tipo = tipo;
    }

    public EstadoTanque getEstado() {
        return estado;
    }

    public void setEstado(EstadoTanque estado) {
        this.estado = estado;
    }

    public BigDecimal getListrosDisponible() {
        return listrosDisponible;
    }

    public void setListrosDisponible(BigDecimal listrosDisponible) {
        this.listrosDisponible = listrosDisponible;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
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

        TanqueDTO tanqueDTO = (TanqueDTO) o;
        if (tanqueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tanqueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TanqueDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", litros=" + getLitros() +
            ", tipo='" + getTipo() + "'" +
            ", estado='" + getEstado() + "'" +
            ", listrosDisponible=" + getListrosDisponible() +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", lote=" + getLoteId() +
            ", lote='" + getLoteCodigo() + "'" +
            ", producto=" + getProductoId() +
            ", producto='" + getProductoNombreComercial() + "'" +
            ", empresa=" + getEmpresaId() +
            ", empresa='" + getEmpresaNombreEmpresa() + "'" +
            "}";
    }
}
