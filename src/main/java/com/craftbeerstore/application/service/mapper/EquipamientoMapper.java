package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.EquipamientoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Equipamiento} and its DTO {@link EquipamientoDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface EquipamientoMapper extends EntityMapper<EquipamientoDTO, Equipamiento> {

    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "empresa.nombreEmpresa", target = "empresaNombreEmpresa")
    EquipamientoDTO toDto(Equipamiento equipamiento);

    @Mapping(source = "empresaId", target = "empresa")
    Equipamiento toEntity(EquipamientoDTO equipamientoDTO);

    default Equipamiento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Equipamiento equipamiento = new Equipamiento();
        equipamiento.setId(id);
        return equipamiento;
    }
}
