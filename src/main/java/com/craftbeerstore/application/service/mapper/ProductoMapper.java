package com.craftbeerstore.application.service.mapper;

import com.craftbeerstore.application.domain.*;
import com.craftbeerstore.application.service.dto.ProductoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producto} and its DTO {@link ProductoDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmpresaMapper.class, EstilosMapper.class})
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {

    @Mapping(source = "empresa.id", target = "empresaId")
    @Mapping(source = "estilos.id", target = "estilosId")
    @Mapping(source = "estilos.nombreEstilo", target = "estilosNombreEstilo")
    ProductoDTO toDto(Producto producto);

    @Mapping(source = "empresaId", target = "empresa")
    @Mapping(source = "estilosId", target = "estilos")
    Producto toEntity(ProductoDTO productoDTO);

    default Producto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(id);
        return producto;
    }
}
