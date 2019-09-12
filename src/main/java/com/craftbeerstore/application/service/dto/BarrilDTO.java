package com.craftbeerstore.application.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.craftbeerstore.application.domain.enumeration.LitrosBarril;
import com.craftbeerstore.application.domain.enumeration.ConectorBarril;
import com.craftbeerstore.application.domain.enumeration.EstadoBarril;

/**
 * A DTO for the Barril entity.
 */
public class BarrilDTO implements Serializable {

    private Long id;

    private String codigo;

    private LitrosBarril litros;

    private ConectorBarril conector;

    private EstadoBarril estado;

    @Lob
    private byte[] imagen;

    private String imagenContentType;

    private Long empresaId;

    private String empresaNombreEmpresa;

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LitrosBarril getLitros() {
        return litros;
    }

    public void setLitros(LitrosBarril litros) {
        this.litros = litros;
    }

    public ConectorBarril getConector() {
        return conector;
    }

    public void setConector(ConectorBarril conector) {
        this.conector = conector;
    }

    public EstadoBarril getEstado() {
        return estado;
    }

    public void setEstado(EstadoBarril estado) {
        this.estado = estado;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
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

        BarrilDTO barrilDTO = (BarrilDTO) o;
        if (barrilDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), barrilDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BarrilDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", litros='" + getLitros() + "'" +
            ", conector='" + getConector() + "'" +
            ", estado='" + getEstado() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", empresa=" + getEmpresaId() +
            ", empresa='" + getEmpresaNombreEmpresa() + "'" +
            ", lote=" + getLoteId() +
            ", lote='" + getLoteCodigo() + "'" +
            ", cliente=" + getClienteId() +
            ", cliente='" + getClienteNombreApellido() + "'" +
            "}";
    }
}
