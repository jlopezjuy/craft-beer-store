package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.CompraInsumo;
import com.craftbeerstore.application.service.dto.CompraInsumoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity CompraInsumo and its DTO CompraInsumoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProveedorMapper.class, EmpresaMapper.class})
public interface CompraInsumoMapper extends EntityMapper<CompraInsumoDTO, CompraInsumo> {

  @Mapping(source = "proveedor.id", target = "proveedorId")
  @Mapping(source = "proveedor.nombreProveedor", target = "proveedorNombreProveedor")
  @Mapping(source = "empresa.id", target = "empresaId")
  @Mapping(source = "empresa.nombreEmpresa", target = "empresaNombreEmpresa")
  CompraInsumoDTO toDto(CompraInsumo compraInsumo);

  @Mapping(source = "proveedorId", target = "proveedor")
  @Mapping(source = "empresaId", target = "empresa")
  CompraInsumo toEntity(CompraInsumoDTO compraInsumoDTO);

  default CompraInsumo fromId(Long id) {
    if (id == null) {
      return null;
    }
    CompraInsumo compraInsumo = new CompraInsumo();
    compraInsumo.setId(id);
    return compraInsumo;
  }
}
