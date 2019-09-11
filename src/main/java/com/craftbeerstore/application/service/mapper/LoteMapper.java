package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.LoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lote and its DTO LoteDTO.
 */
@Mapper(componentModel = "spring", uses = {RecetaMapper.class})
public interface LoteMapper extends EntityMapper<LoteDTO, Lote> {

    @Mapping(source = "receta.id", target = "recetaId")
    @Mapping(source = "receta.nombre", target = "recetaNombre")
    LoteDTO toDto(Lote lote);

    @Mapping(source = "recetaId", target = "receta")
    Lote toEntity(LoteDTO loteDTO);

    default Lote fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lote lote = new Lote();
        lote.setId(id);
        return lote;
    }
}
