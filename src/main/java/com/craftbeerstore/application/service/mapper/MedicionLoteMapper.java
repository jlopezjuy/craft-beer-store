package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.MedicionLoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MedicionLote and its DTO MedicionLoteDTO.
 */
@Mapper(componentModel = "spring", uses = {LoteMapper.class, TanqueMapper.class})
public interface MedicionLoteMapper extends EntityMapper<MedicionLoteDTO, MedicionLote> {

    @Mapping(source = "lote.id", target = "loteId")
    @Mapping(source = "lote.codigo", target = "loteCodigo")
    @Mapping(source = "tanque.id", target = "tanqueId")
    @Mapping(source = "tanque.nombre", target = "tanqueNombre")
    MedicionLoteDTO toDto(MedicionLote medicionLote);

    @Mapping(source = "loteId", target = "lote")
    @Mapping(source = "tanqueId", target = "tanque")
    MedicionLote toEntity(MedicionLoteDTO medicionLoteDTO);

    default MedicionLote fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicionLote medicionLote = new MedicionLote();
        medicionLote.setId(id);
        return medicionLote;
    }
}
