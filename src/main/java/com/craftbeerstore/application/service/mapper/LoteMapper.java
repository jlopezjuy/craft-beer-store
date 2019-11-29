package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.LoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lote} and its DTO {@link LoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {RecetaMapper.class, EmpresaMapper.class, ProductoMapper.class, TanqueMapper.class})
public interface LoteMapper extends EntityMapper<LoteDTO, Lote> {

    @Mapping(source = "receta.id", target = "recetaId")
    @Mapping(source = "receta.nombre", target = "recetaNombre")
    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nombreEmpresa", target = "empresaNombreEmpresa")
    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombreComercial", target = "productoNombreComercial")
    @Mapping(source = "tanque.id", target = "tanqueId")
    @Mapping(source = "tanque.nombre", target = "tanqueNombre")
    LoteDTO toDto(Lote lote);

    @Mapping(source = "recetaId", target = "receta")
    @Mapping(source = "empresaId", target = "empresa")
    @Mapping(source = "productoId", target = "producto")
    @Mapping(source = "tanqueId", target = "tanque")
    Lote toEntity(LoteDTO loteDTO);

    default Lote fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lote lote = new Lote();
        lote.setId(id);
        return lote;
    }
}
