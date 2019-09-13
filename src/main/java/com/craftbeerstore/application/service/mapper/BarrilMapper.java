package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.Barril;
import com.craftbeerstore.application.service.dto.BarrilDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Barril and its DTO BarrilDTO.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class, LoteMapper.class, ClienteMapper.class})
public interface BarrilMapper extends EntityMapper<BarrilDTO, Barril> {

  @Mapping(source = "empresa.id", target = "empresaId")
  @Mapping(source = "empresa.nombreEmpresa", target = "empresaNombreEmpresa")
  @Mapping(source = "lote.id", target = "loteId")
  @Mapping(source = "lote.codigo", target = "loteCodigo")
  @Mapping(source = "cliente.id", target = "clienteId")
  @Mapping(source = "cliente.nombreApellido", target = "clienteNombreApellido")
  BarrilDTO toDto(Barril barril);

  @Mapping(source = "empresaId", target = "empresa")
  @Mapping(source = "loteId", target = "lote")
  @Mapping(source = "clienteId", target = "cliente")
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
