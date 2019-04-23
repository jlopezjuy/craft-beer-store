package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Caja;
import com.craftbeerstore.application.domain.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Caja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CajaRepository extends JpaRepository<Caja, Long> {

    Page<Caja> findAllByEmpresa(Pageable pageable, Empresa empresa);
}
