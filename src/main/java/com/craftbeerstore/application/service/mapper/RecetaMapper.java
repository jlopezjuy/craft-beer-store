package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.RecetaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Receta and its DTO RecetaDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface RecetaMapper extends EntityMapper<RecetaDTO, Receta> {

    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombreComercial", target = "productoNombreComercial")
    RecetaDTO toDto(Receta receta);

    @Mapping(source = "productoId", target = "producto")
    Receta toEntity(RecetaDTO recetaDTO);

    default Receta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Receta receta = new Receta();
        receta.setId(id);
        return receta;
    }
}
