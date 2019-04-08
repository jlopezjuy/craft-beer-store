package com.craftbeerstore.application.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import com.craftbeerstore.application.domain.enumeration.EstiloCerveza;
import com.craftbeerstore.application.domain.enumeration.TipoProducto;

/**
 * A DTO for the Producto entity.
 */
public class ProductoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreProducto;

    private EstiloCerveza estilo;

    private String nombreComercial;

    @NotNull
    private BigDecimal precioLitro;

    private TipoProducto tipoProducto;

    @Lob
    private byte[] imagen;

    private String imagenContentType;

    private Long empresaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public EstiloCerveza getEstilo() {
        return estilo;
    }

    public void setEstilo(EstiloCerveza estilo) {
        this.estilo = estilo;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public BigDecimal getPrecioLitro() {
        return precioLitro;
    }

    public void setPrecioLitro(BigDecimal precioLitro) {
        this.precioLitro = precioLitro;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductoDTO productoDTO = (ProductoDTO) o;
        if (productoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
            "id=" + getId() +
            ", nombreProducto='" + getNombreProducto() + "'" +
            ", estilo='" + getEstilo() + "'" +
            ", nombreComercial='" + getNombreComercial() + "'" +
            ", precioLitro=" + getPrecioLitro() +
            ", tipoProducto='" + getTipoProducto() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", empresa=" + getEmpresaId() +
            "}";
    }
}
