package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.InsumoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Insumo and its DTO InsumoDTO.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface InsumoMapper extends EntityMapper<InsumoDTO, Insumo> {

    @Mapping(source = "empresa.id", target = "empresaId")
    InsumoDTO toDto(Insumo insumo);

    @Mapping(source = "empresaId", target = "empresa")
    Insumo toEntity(InsumoDTO insumoDTO);

    default Insumo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Insumo insumo = new Insumo();
        insumo.setId(id);
        return insumo;
    }
}
