package com.craftbeerstore.application.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.craftbeerstore.application.domain.enumeration.CondicionFiscal;
import com.craftbeerstore.application.domain.enumeration.Provincia;

/**
 * A DTO for the Proveedor entity.
 */
public class ProveedorDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombreProveedor;

    @NotNull
    private String razonSocial;

    @NotNull
    private String cuit;

    @NotNull
//    @Pattern(regexp = "\\(\\d{3}\\)\\d{3}-?\\d{4}")
    private String telefono;

    @NotNull
    private LocalDate fechaAlta;

    @NotNull
    private String domicilio;

    @NotNull
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    private String email;

    @Lob
    private String notas;

    private CondicionFiscal condicionFiscal;

    private String localidad;

    private Long codigoPostal;

    private Provincia provincia;

    private String contacto;


    private Long empresaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public CondicionFiscal getCondicionFiscal() {
        return condicionFiscal;
    }

    public void setCondicionFiscal(CondicionFiscal condicionFiscal) {
        this.condicionFiscal = condicionFiscal;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Long getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Long codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
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

        ProveedorDTO proveedorDTO = (ProveedorDTO) o;
        if (proveedorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proveedorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProveedorDTO{" +
            "id=" + getId() +
            ", nombreProveedor='" + getNombreProveedor() + "'" +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", cuit='" + getCuit() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fechaAlta='" + getFechaAlta() + "'" +
            ", domicilio='" + getDomicilio() + "'" +
            ", email='" + getEmail() + "'" +
            ", notas='" + getNotas() + "'" +
            ", condicionFiscal='" + getCondicionFiscal() + "'" +
            ", localidad='" + getLocalidad() + "'" +
            ", codigoPostal=" + getCodigoPostal() +
            ", provincia='" + getProvincia() + "'" +
            ", contacto='" + getContacto() + "'" +
            ", empresa=" + getEmpresaId() +
            "}";
    }
}
