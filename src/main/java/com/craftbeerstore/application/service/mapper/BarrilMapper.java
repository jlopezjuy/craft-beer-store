package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.BarrilDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Barril and its DTO BarrilDTO.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface BarrilMapper extends EntityMapper<BarrilDTO, Barril> {

    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nombreEmpresa", target = "empresaNombreEmpresa")
    BarrilDTO toDto(Barril barril);

    @Mapping(source = "empresaId", target = "empresa")
    Barril toEntity(BarrilDTO barrilDTO);

    default Barril fromId(Long id) {
        if (id == null) {
            return null;
        }
        Barril barril = new Barril();
        barril.setId(id);
        return barril;
    }
}
