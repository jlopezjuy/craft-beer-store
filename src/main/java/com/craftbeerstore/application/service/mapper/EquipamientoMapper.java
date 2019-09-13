package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.Equipamiento;
import com.craftbeerstore.application.service.dto.EquipamientoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Equipamiento and its DTO EquipamientoDTO.
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
