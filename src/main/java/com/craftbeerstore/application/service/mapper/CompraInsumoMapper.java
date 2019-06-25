package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.CompraInsumoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CompraInsumo and its DTO CompraInsumoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProveedorMapper.class})
public interface CompraInsumoMapper extends EntityMapper<CompraInsumoDTO, CompraInsumo> {

    @Mapping(source = "proveedor.id", target = "proveedorId")
    @Mapping(source = "proveedor.nombreProveedor", target = "proveedorNombreProveedor")
    CompraInsumoDTO toDto(CompraInsumo compraInsumo);

    @Mapping(source = "proveedorId", target = "proveedor")
    CompraInsumo toEntity(CompraInsumoDTO compraInsumoDTO);

    default CompraInsumo fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompraInsumo compraInsumo = new CompraInsumo();
        compraInsumo.setId(id);
        return compraInsumo;
    }
}
