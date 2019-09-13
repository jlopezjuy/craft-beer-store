package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.Cliente;
import com.craftbeerstore.application.service.dto.ClienteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Cliente and its DTO ClienteDTO.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class})
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {

  @Mapping(source = "empresa.id", target = "empresaId")
  ClienteDTO toDto(Cliente cliente);

  @Mapping(source = "empresaId", target = "empresa")
  Cliente toEntity(ClienteDTO clienteDTO);

  default Cliente fromId(Long id) {
    if (id == null) {
      return null;
    }
    Cliente cliente = new Cliente();
    cliente.setId(id);
    return cliente;
  }
}
