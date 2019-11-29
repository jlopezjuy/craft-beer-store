package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.InsumoRecomendadoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InsumoRecomendado} and its DTO {@link InsumoRecomendadoDTO}.
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
