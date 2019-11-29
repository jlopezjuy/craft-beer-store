package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.CompraInsumoDetalleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompraInsumoDetalle} and its DTO {@link CompraInsumoDetalleDTO}.
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
