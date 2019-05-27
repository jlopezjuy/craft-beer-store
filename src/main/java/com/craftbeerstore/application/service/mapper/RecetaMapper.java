package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.RecetaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Receta and its DTO RecetaDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class, InsumoMapper.class})
public interface RecetaMapper extends EntityMapper<RecetaDTO, Receta> {

    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombreComercial", target = "productoNombreComercial")
    @Mapping(source = "maltas.id", target = "maltasId")
    @Mapping(source = "maltas.nombreInsumo", target = "maltasNombreInsumo")
    @Mapping(source = "lupulo.id", target = "lupuloId")
    @Mapping(source = "lupulo.nombreInsumo", target = "lupuloNombreInsumo")
    @Mapping(source = "levadura.id", target = "levaduraId")
    @Mapping(source = "levadura.nombreInsumo", target = "levaduraNombreInsumo")
    @Mapping(source = "otros.id", target = "otrosId")
    @Mapping(source = "otros.nombreInsumo", target = "otrosNombreInsumo")
    RecetaDTO toDto(Receta receta);

    @Mapping(source = "productoId", target = "producto")
    @Mapping(source = "maltasId", target = "maltas")
    @Mapping(source = "lupuloId", target = "lupulo")
    @Mapping(source = "levaduraId", target = "levadura")
    @Mapping(source = "otrosId", target = "otros")
    Receta toEntity(RecetaDTO recetaDTO);

    default Receta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Receta receta = new Receta();
        receta.setId(id);
        return receta;
    }
}
