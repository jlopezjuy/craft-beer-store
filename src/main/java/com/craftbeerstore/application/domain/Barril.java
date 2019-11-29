package com.craftbeerstore.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.craftbeerstore.application.domain.enumeration.LitrosBarril;

import com.craftbeerstore.application.domain.enumeration.ConectorBarril;

import com.craftbeerstore.application.domain.enumeration.EstadoBarril;

/**
 * A Barril.
 */
@Entity
@Table(name = "barril")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Barril implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "litros")
    private LitrosBarril litros;

    @Enumerated(EnumType.STRING)
    @Column(name = "conector")
    private ConectorBarril conector;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoBarril estado;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @ManyToOne
    @JsonIgnoreProperties("barrils")
    private Empresa empresa;

    @ManyToOne
    @JsonIgnoreProperties("barrils")
    private Lote lote;

    @ManyToOne
    @JsonIgnoreProperties("barrils")
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Barril codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LitrosBarril getLitros() {
        return litros;
    }

    public Barril litros(LitrosBarril litros) {
        this.litros = litros;
        return this;
    }

    public void setLitros(LitrosBarril litros) {
        this.litros = litros;
    }

    public ConectorBarril getConector() {
        return conector;
    }

    public Barril conector(ConectorBarril conector) {
        this.conector = conector;
        return this;
    }

    public void setConector(ConectorBarril conector) {
        this.conector = conector;
    }

    public EstadoBarril getEstado() {
        return estado;
    }

    public Barril estado(EstadoBarril estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoBarril estado) {
        this.estado = estado;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Barril imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Barril imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Barril empresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Lote getLote() {
        return lote;
    }

    public Barril lote(Lote lote) {
        this.lote = lote;
        return this;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Barril cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Barril)) {
            return false;
        }
        return id != null && id.equals(((Barril) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Barril{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", litros='" + getLitros() + "'" +
            ", conector='" + getConector() + "'" +
            ", estado='" + getEstado() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            "}";
    }
}
