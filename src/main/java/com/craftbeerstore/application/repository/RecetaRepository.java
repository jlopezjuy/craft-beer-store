package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Producto;
import com.craftbeerstore.application.domain.Receta;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Receta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    /**
     *
     * @param pageable
     * @param producto
     * @return
     */
    Page<Receta> findAllByProducto(Pageable pageable, Producto producto);

    /**
     *
     * @param producto
     * @return
     */
    List<Receta> findAllByProducto(Producto producto);
}
