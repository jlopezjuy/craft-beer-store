package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.MovimientoBarrilDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MovimientoBarril and its DTO MovimientoBarrilDTO.
 */
@Mapper(componentModel = "spring", uses = {BarrilMapper.class, ProductoMapper.class, LoteMapper.class, ClienteMapper.class})
public interface MovimientoBarrilMapper extends EntityMapper<MovimientoBarrilDTO, MovimientoBarril> {

    @Mapping(source = "barril.id", target = "barrilId")
    @Mapping(source = "barril.codigo", target = "barrilCodigo")
    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombreComercial", target = "productoNombreComercial")
    @Mapping(source = "lote.id", target = "loteId")
    @Mapping(source = "lote.codigo", target = "loteCodigo")
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombreApellido", target = "clienteNombreApellido")
    MovimientoBarrilDTO toDto(MovimientoBarril movimientoBarril);

    @Mapping(source = "barrilId", target = "barril")
    @Mapping(source = "productoId", target = "producto")
    @Mapping(source = "loteId", target = "lote")
    @Mapping(source = "clienteId", target = "cliente")
    MovimientoBarril toEntity(MovimientoBarrilDTO movimientoBarrilDTO);

    default MovimientoBarril fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovimientoBarril movimientoBarril = new MovimientoBarril();
        movimientoBarril.setId(id);
        return movimientoBarril;
    }
}
