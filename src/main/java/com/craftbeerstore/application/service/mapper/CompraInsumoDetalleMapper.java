package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.CompraInsumoDetalle;
import com.craftbeerstore.application.service.dto.CompraInsumoDetalleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity CompraInsumoDetalle and its DTO CompraInsumoDetalleDTO.
 */
@Mapper(componentModel = "spring", uses = {CompraInsumoMapper.class, InsumoRecomendadoMapper.class})
public interface CompraInsumoDetalleMapper extends EntityMapper<CompraInsumoDetalleDTO, CompraInsumoDetalle> {

  @Mapping(source = "compraInsumo.id", target = "compraInsumoId")
  @Mapping(source = "compraInsumo.nroFactura", target = "compraInsumoNroFactura")
  @Mapping(source = "insumoRecomendado.id", target = "insumoRecomendadoId")
  @Mapping(source = "insumoRecomendado.nombre", target = "insumoRecomendadoNombre")
  CompraInsumoDetalleDTO toDto(CompraInsumoDetalle compraInsumoDetalle);

  @Mapping(source = "compraInsumoId", target = "compraInsumo")
  @Mapping(source = "insumoRecomendadoId", target = "insumoRecomendado")
  CompraInsumoDetalle toEntity(CompraInsumoDetalleDTO compraInsumoDetalleDTO);

  default CompraInsumoDetalle fromId(Long id) {
    if (id == null) {
      return null;
    }
    CompraInsumoDetalle compraInsumoDetalle = new CompraInsumoDetalle();
    compraInsumoDetalle.setId(id);
    return compraInsumoDetalle;
  }
}
