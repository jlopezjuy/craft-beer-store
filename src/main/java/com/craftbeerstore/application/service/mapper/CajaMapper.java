package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.CajaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Caja and its DTO CajaDTO.
 */
@Mapper(componentModel = "spring", uses = {ProveedorMapper.class, ClienteMapper.class, EmpresaMapper.class})
public interface CajaMapper extends EntityMapper<CajaDTO, Caja> {

    @Mapping(source = "proveedor.id", target = "proveedorId")
    @Mapping(source = "proveedor.nombreProveedor", target = "proveedorNombreProveedor")
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombeApellido", target = "clienteNombeApellido")
    @Mapping(source = "empresa.id", target = "empresaId")
    CajaDTO toDto(Caja caja);

    @Mapping(source = "proveedorId", target = "proveedor")
    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(source = "empresaId", target = "empresa")
    Caja toEntity(CajaDTO cajaDTO);

    default Caja fromId(Long id) {
        if (id == null) {
            return null;
        }
        Caja caja = new Caja();
        caja.setId(id);
        return caja;
    }
}
