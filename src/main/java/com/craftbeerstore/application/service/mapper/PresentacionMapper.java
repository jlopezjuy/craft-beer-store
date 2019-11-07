package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.PresentacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Presentacion and its DTO PresentacionDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface PresentacionMapper extends EntityMapper<PresentacionDTO, Presentacion> {

  @Mapping(source = "producto.id", target = "productoId")
  @Mapping(source = "producto.nombreComercial", target = "productoNombreComercial")
  PresentacionDTO toDto(Presentacion presentacion);

  @Mapping(source = "productoId", target = "producto")
  Presentacion toEntity(PresentacionDTO presentacionDTO);

  default Presentacion fromId(Long id) {
    if (id == null) {
      return null;
    }
    Presentacion presentacion = new Presentacion();
    presentacion.setId(id);
    return presentacion;
  }
}
