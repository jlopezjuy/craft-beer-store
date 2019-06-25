package com.craftbeerstore.application.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.craftbeerstore.application.domain.enumeration.Provincia;

/**
 * A Empresa.
 */
@Entity
@Table(name = "empresa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre_empresa", nullable = false, unique = true)
    private String nombreEmpresa;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "codigo_postal")
    private Long codigoPostal;

    @Enumerated(EnumType.STRING)
    @Column(name = "provincia")
    private Provincia provincia;

    @Column(name = "telefono")
    private String telefono;

    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    @Column(name = "correo", unique = true)
    private String correo;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public Empresa nombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
        return this;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public Empresa direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public Empresa localidad(String localidad) {
        this.localidad = localidad;
        return this;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Long getCodigoPostal() {
        return codigoPostal;
    }

    public Empresa codigoPostal(Long codigoPostal) {
        this.codigoPostal = codigoPostal;
        return this;
    }

    public void setCodigoPostal(Long codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public Empresa provincia(Provincia provincia) {
        this.provincia = provincia;
        return this;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public String getTelefono() {
        return telefono;
    }

    public Empresa telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public Empresa correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public User getUser() {
        return user;
    }

    public Empresa user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Empresa empresa = (Empresa) o;
        if (empresa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empresa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Empresa{" +
            "id=" + getId() +
            ", nombreEmpresa='" + getNombreEmpresa() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", localidad='" + getLocalidad() + "'" +
            ", codigoPostal=" + getCodigoPostal() +
            ", provincia='" + getProvincia() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", correo='" + getCorreo() + "'" +
            "}";
    }
}
