package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.MovimientosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Movimientos} and its DTO {@link MovimientosDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, EmpresaMapper.class, PuntoDeVentaMapper.class})
public interface MovimientosMapper extends EntityMapper<MovimientosDTO, Movimientos> {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombreApellido", target = "clienteNombreApellido")
    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nombreEmpresa", target = "empresaNombreEmpresa")
    @Mapping(source = "puntoDeVenta.id", target = "puntoDeVentaId")
    MovimientosDTO toDto(Movimientos movimientos);

    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(source = "empresaId", target = "empresa")
    @Mapping(source = "puntoDeVentaId", target = "puntoDeVenta")
    Movimientos toEntity(MovimientosDTO movimientosDTO);

    default Movimientos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Movimientos movimientos = new Movimientos();
        movimientos.setId(id);
        return movimientos;
    }
}
