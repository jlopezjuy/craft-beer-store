package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.Insumo;
import com.craftbeerstore.application.service.dto.InsumoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Insumo and its DTO InsumoDTO.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class, InsumoRecomendadoMapper.class})
public interface InsumoMapper extends EntityMapper<InsumoDTO, Insumo> {

  @Mapping(source = "empresa.id", target = "empresaId")
  @Mapping(source = "insumoRecomendado.id", target = "insumoRecomendadoId")
  @Mapping(source = "insumoRecomendado.nombre", target = "insumoRecomendadoNombre")
  InsumoDTO toDto(Insumo insumo);

  @Mapping(source = "empresaId", target = "empresa")
  @Mapping(source = "insumoRecomendadoId", target = "insumoRecomendado")
  Insumo toEntity(InsumoDTO insumoDTO);

  default Insumo fromId(Long id) {
    if (id == null) {
      return null;
    }
    Insumo insumo = new Insumo();
    insumo.setId(id);
    return insumo;
  }
}
