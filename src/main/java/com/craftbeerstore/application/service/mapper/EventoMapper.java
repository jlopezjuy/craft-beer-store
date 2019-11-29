package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.EventoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Evento} and its DTO {@link EventoDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface EventoMapper extends EntityMapper<EventoDTO, Evento> {

    @Mapping(source = "empresa.id", target = "empresaId")
    EventoDTO toDto(Evento evento);

    @Mapping(source = "empresaId", target = "empresa")
    Evento toEntity(EventoDTO eventoDTO);

    default Evento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Evento evento = new Evento();
        evento.setId(id);
        return evento;
    }
}
