package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Presentacion;
import com.craftbeerstore.application.domain.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Presentacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PresentacionRepository extends JpaRepository<Presentacion, Long> {

    Page<Presentacion> findAllByProducto(Pageable pageable, Producto producto);

    @Query(value = "select * from presentacion where producto_id in( select producto_id from producto where empresa_id = :empresaId) order by producto_id",
    nativeQuery = true)
    Page<Presentacion> getAllPresentationsByEmpresa(Pageable pageable, @Param("empresaId")Long empresaId);
}
