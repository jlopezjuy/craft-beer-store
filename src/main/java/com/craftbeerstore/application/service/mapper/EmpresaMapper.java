package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.EmpresaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empresa} and its DTO {@link EmpresaDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface EmpresaMapper extends EntityMapper<EmpresaDTO, Empresa> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    EmpresaDTO toDto(Empresa empresa);

    @Mapping(source = "userId", target = "user")
    Empresa toEntity(EmpresaDTO empresaDTO);

    default Empresa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Empresa empresa = new Empresa();
        empresa.setId(id);
        return empresa;
    }
}
