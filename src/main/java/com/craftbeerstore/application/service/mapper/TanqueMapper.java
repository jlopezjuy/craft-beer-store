package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.TanqueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tanque} and its DTO {@link TanqueDTO}.
 */
@Mapper(componentModel = "spring", uses = {LoteMapper.class, ProductoMapper.class, EmpresaMapper.class})
public interface TanqueMapper extends EntityMapper<TanqueDTO, Tanque> {

    @Mapping(source = "lote.id", target = "loteId")
    @Mapping(source = "lote.codigo", target = "loteCodigo")
    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombreComercial", target = "productoNombreComercial")
    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nombreEmpresa", target = "empresaNombreEmpresa")
    TanqueDTO toDto(Tanque tanque);

    @Mapping(source = "loteId", target = "lote")
    @Mapping(source = "productoId", target = "producto")
    @Mapping(source = "empresaId", target = "empresa")
    Tanque toEntity(TanqueDTO tanqueDTO);

    default Tanque fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tanque tanque = new Tanque();
        tanque.setId(id);
        return tanque;
    }
}
