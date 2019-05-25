package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.RecetaInsumoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RecetaInsumo and its DTO RecetaInsumoDTO.
 */
@Mapper(componentModel = "spring", uses = {InsumoMapper.class, RecetaMapper.class})
public interface RecetaInsumoMapper extends EntityMapper<RecetaInsumoDTO, RecetaInsumo> {

    @Mapping(source = "insumo.id", target = "insumoId")
    @Mapping(source = "insumo.nombreInsumo", target = "insumoNombreInsumo")
    @Mapping(source = "receta.id", target = "recetaId")
    RecetaInsumoDTO toDto(RecetaInsumo recetaInsumo);

    @Mapping(source = "insumoId", target = "insumo")
    @Mapping(source = "recetaId", target = "receta")
    RecetaInsumo toEntity(RecetaInsumoDTO recetaInsumoDTO);

    default RecetaInsumo fromId(Long id) {
        if (id == null) {
            return null;
        }
        RecetaInsumo recetaInsumo = new RecetaInsumo();
        recetaInsumo.setId(id);
        return recetaInsumo;
    }
}
