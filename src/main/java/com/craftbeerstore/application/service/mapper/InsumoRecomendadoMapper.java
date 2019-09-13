package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.InsumoRecomendado;
import com.craftbeerstore.application.service.dto.InsumoRecomendadoDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity InsumoRecomendado and its DTO InsumoRecomendadoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InsumoRecomendadoMapper extends EntityMapper<InsumoRecomendadoDTO, InsumoRecomendado> {


  default InsumoRecomendado fromId(Long id) {
    if (id == null) {
      return null;
    }
    InsumoRecomendado insumoRecomendado = new InsumoRecomendado();
    insumoRecomendado.setId(id);
    return insumoRecomendado;
  }
}
