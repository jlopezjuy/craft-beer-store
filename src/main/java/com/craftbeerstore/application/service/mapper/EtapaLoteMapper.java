package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.EtapaLoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EtapaLote and its DTO EtapaLoteDTO.
 */
@Mapper(componentModel = "spring", uses = {TanqueMapper.class, LoteMapper.class})
public interface EtapaLoteMapper extends EntityMapper<EtapaLoteDTO, EtapaLote> {

    @Mapping(source = "tanque.id", target = "tanqueId")
    @Mapping(source = "tanque.nombre", target = "tanqueNombre")
    @Mapping(source = "lote.id", target = "loteId")
    @Mapping(source = "lote.codigo", target = "loteCodigo")
    EtapaLoteDTO toDto(EtapaLote etapaLote);

    @Mapping(source = "tanqueId", target = "tanque")
    @Mapping(source = "loteId", target = "lote")
    EtapaLote toEntity(EtapaLoteDTO etapaLoteDTO);

    default EtapaLote fromId(Long id) {
        if (id == null) {
            return null;
        }
        EtapaLote etapaLote = new EtapaLote();
        etapaLote.setId(id);
        return etapaLote;
    }
}
