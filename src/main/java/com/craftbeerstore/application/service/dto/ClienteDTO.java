package com.craftbeerstore.application.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.craftbeerstore.application.domain.enumeration.TipoCliente;

/**
 * A DTO for the Cliente entity.
 */
public class ClienteDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombeApellido;

    @NotNull
    private String domicilio;

    @NotNull
    private TipoCliente tipoCliente;

    private String telefono;

    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    private String correo;


    private Long empresaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombeApellido() {
        return nombeApellido;
    }

    public void setNombeApellido(String nombeApellido) {
        this.nombeApellido = nombeApellido;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

        ClienteDTO clienteDTO = (ClienteDTO) o;
        if (clienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
            "id=" + getId() +
            ", nombeApellido='" + getNombeApellido() + "'" +
            ", domicilio='" + getDomicilio() + "'" +
            ", tipoCliente='" + getTipoCliente() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", empresa=" + getEmpresaId() +
            "}";
    }
}
