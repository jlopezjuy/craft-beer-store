package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.EstilosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Estilos and its DTO EstilosDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstilosMapper extends EntityMapper<EstilosDTO, Estilos> {



    default Estilos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Estilos estilos = new Estilos();
        estilos.setId(id);
        return estilos;
    }
}
