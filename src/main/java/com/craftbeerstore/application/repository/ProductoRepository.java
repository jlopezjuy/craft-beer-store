package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Producto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Page<Producto> findAllByEmpresa(Pageable pageable, Empresa empresa);
}
