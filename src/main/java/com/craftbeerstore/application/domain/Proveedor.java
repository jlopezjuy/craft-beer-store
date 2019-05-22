package com.craftbeerstore.application.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.craftbeerstore.application.domain.enumeration.CondicionFiscal;

import com.craftbeerstore.application.domain.enumeration.Provincia;

/**
 * A Proveedor.
 */
@Entity
@Table(name = "proveedor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "proveedor")
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre_proveedor", nullable = false)
    private String nombreProveedor;

    @NotNull
    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @NotNull
    @Column(name = "cuit", nullable = false)
    private String cuit;

    @NotNull
//    @Pattern(regexp = "\\(\\d{3}\\)\\d{3}-?\\d{4}")
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @NotNull
    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @NotNull
    @Column(name = "domicilio", nullable = false)
    private String domicilio;

    @NotNull
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    @Column(name = "email", nullable = false)
    private String email;

    @Lob
    @Column(name = "notas")
    private String notas;

    @Enumerated(EnumType.STRING)
    @Column(name = "condicion_fiscal")
    private CondicionFiscal condicionFiscal;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "codigo_postal")
    private Long codigoPostal;

    @Enumerated(EnumType.STRING)
    @Column(name = "provincia")
    private Provincia provincia;

    @Column(name = "contacto")
    private String contacto;

    @ManyToOne
    @JsonIgnoreProperties("proveedors")
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public Proveedor nombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
        return this;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public Proveedor razonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
        return this;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public Proveedor cuit(String cuit) {
        this.cuit = cuit;
        return this;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getTelefono() {
        return telefono;
    }

    public Proveedor telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public Proveedor fechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
        return this;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public Proveedor domicilio(String domicilio) {
        this.domicilio = domicilio;
        return this;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getEmail() {
        return email;
    }

    public Proveedor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotas() {
        return notas;
    }

    public Proveedor notas(String notas) {
        this.notas = notas;
        return this;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public CondicionFiscal getCondicionFiscal() {
        return condicionFiscal;
    }

    public Proveedor condicionFiscal(CondicionFiscal condicionFiscal) {
        this.condicionFiscal = condicionFiscal;
        return this;
    }

    public void setCondicionFiscal(CondicionFiscal condicionFiscal) {
        this.condicionFiscal = condicionFiscal;
    }

    public String getLocalidad() {
        return localidad;
    }

    public Proveedor localidad(String localidad) {
        this.localidad = localidad;
        return this;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Long getCodigoPostal() {
        return codigoPostal;
    }

    public Proveedor codigoPostal(Long codigoPostal) {
        this.codigoPostal = codigoPostal;
        return this;
    }

    public void setCodigoPostal(Long codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public Proveedor provincia(Provincia provincia) {
        this.provincia = provincia;
        return this;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public String getContacto() {
        return contacto;
    }

    public Proveedor contacto(String contacto) {
        this.contacto = contacto;
        return this;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Proveedor empresa(Empresa empresa) {
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
        Proveedor proveedor = (Proveedor) o;
        if (proveedor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proveedor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Proveedor{" +
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
            "}";
    }
}
