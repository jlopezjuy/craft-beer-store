package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.ProveedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Proveedor and its DTO ProveedorDTO.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface ProveedorMapper extends EntityMapper<ProveedorDTO, Proveedor> {

    @Mapping(source = "empresa.id", target = "empresaId")
    ProveedorDTO toDto(Proveedor proveedor);

    @Mapping(source = "empresaId", target = "empresa")
    Proveedor toEntity(ProveedorDTO proveedorDTO);

    default Proveedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Proveedor proveedor = new Proveedor();
        proveedor.setId(id);
        return proveedor;
    }
}
