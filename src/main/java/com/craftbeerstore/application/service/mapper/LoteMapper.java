package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.Lote;
import com.craftbeerstore.application.service.dto.LoteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Lote and its DTO LoteDTO.
 */
@Mapper(componentModel = "spring", uses = {RecetaMapper.class, EmpresaMapper.class})
public interface LoteMapper extends EntityMapper<LoteDTO, Lote> {

  @Mapping(source = "receta.id", target = "recetaId")
  @Mapping(source = "receta.nombre", target = "recetaNombre")
  @Mapping(source = "empresa.id", target = "empresaId")
  @Mapping(source = "empresa.nombreEmpresa", target = "empresaNombreEmpresa")
  LoteDTO toDto(Lote lote);

  @Mapping(source = "recetaId", target = "receta")
  @Mapping(source = "empresaId", target = "empresa")
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
