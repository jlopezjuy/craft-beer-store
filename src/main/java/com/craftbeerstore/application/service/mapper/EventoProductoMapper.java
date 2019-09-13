package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.EventoProducto;
import com.craftbeerstore.application.service.dto.EventoProductoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity EventoProducto and its DTO EventoProductoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class, EventoMapper.class})
public interface EventoProductoMapper extends EntityMapper<EventoProductoDTO, EventoProducto> {

  @Mapping(source = "producto.id", target = "productoId")
  @Mapping(source = "producto.descripcion", target = "productoDescripcion")
  @Mapping(source = "evento.id", target = "eventoId")
  EventoProductoDTO toDto(EventoProducto eventoProducto);

  @Mapping(source = "productoId", target = "producto")
  @Mapping(source = "eventoId", target = "evento")
  EventoProducto toEntity(EventoProductoDTO eventoProductoDTO);

  default EventoProducto fromId(Long id) {
    if (id == null) {
      return null;
    }
    EventoProducto eventoProducto = new EventoProducto();
    eventoProducto.setId(id);
    return eventoProducto;
  }
}
