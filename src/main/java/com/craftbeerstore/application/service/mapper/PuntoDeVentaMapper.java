package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.PuntoDeVenta;
import com.craftbeerstore.application.service.dto.PuntoDeVentaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity PuntoDeVenta and its DTO PuntoDeVentaDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface PuntoDeVentaMapper extends EntityMapper<PuntoDeVentaDTO, PuntoDeVenta> {

  @Mapping(source = "cliente.id", target = "clienteId")
  @Mapping(source = "cliente.nombreApellido", target = "clienteNombreApellido")
  PuntoDeVentaDTO toDto(PuntoDeVenta puntoDeVenta);

  @Mapping(source = "clienteId", target = "cliente")
  PuntoDeVenta toEntity(PuntoDeVentaDTO puntoDeVentaDTO);

  default PuntoDeVenta fromId(Long id) {
    if (id == null) {
      return null;
    }
    PuntoDeVenta puntoDeVenta = new PuntoDeVenta();
    puntoDeVenta.setId(id);
    return puntoDeVenta;
  }
}
