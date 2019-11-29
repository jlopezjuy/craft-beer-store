package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.DetalleMovimientoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetalleMovimiento} and its DTO {@link DetalleMovimientoDTO}.
 */
@Mapper(componentModel = "spring", uses = {PresentacionMapper.class, MovimientosMapper.class})
public interface DetalleMovimientoMapper extends EntityMapper<DetalleMovimientoDTO, DetalleMovimiento> {

    @Mapping(source = "presentacion.id", target = "presentacionId")
    @Mapping(source = "movimientos.id", target = "movimientosId")
    DetalleMovimientoDTO toDto(DetalleMovimiento detalleMovimiento);

    @Mapping(source = "presentacionId", target = "presentacion")
    @Mapping(source = "movimientosId", target = "movimientos")
    DetalleMovimiento toEntity(DetalleMovimientoDTO detalleMovimientoDTO);

    default DetalleMovimiento fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetalleMovimiento detalleMovimiento = new DetalleMovimiento();
        detalleMovimiento.setId(id);
        return detalleMovimiento;
    }
}
