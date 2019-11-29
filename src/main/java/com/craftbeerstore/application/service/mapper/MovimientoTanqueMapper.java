package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.MovimientoTanqueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MovimientoTanque} and its DTO {@link MovimientoTanqueDTO}.
 */
@Mapper(componentModel = "spring", uses = {TanqueMapper.class, ProductoMapper.class, LoteMapper.class})
public interface MovimientoTanqueMapper extends EntityMapper<MovimientoTanqueDTO, MovimientoTanque> {

    @Mapping(source = "tanque.id", target = "tanqueId")
    @Mapping(source = "tanque.nombre", target = "tanqueNombre")
    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombreComercial", target = "productoNombreComercial")
    @Mapping(source = "lote.id", target = "loteId")
    @Mapping(source = "lote.codigo", target = "loteCodigo")
    MovimientoTanqueDTO toDto(MovimientoTanque movimientoTanque);

    @Mapping(source = "tanqueId", target = "tanque")
    @Mapping(source = "productoId", target = "producto")
    @Mapping(source = "loteId", target = "lote")
    MovimientoTanque toEntity(MovimientoTanqueDTO movimientoTanqueDTO);

    default MovimientoTanque fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovimientoTanque movimientoTanque = new MovimientoTanque();
        movimientoTanque.setId(id);
        return movimientoTanque;
    }
}
