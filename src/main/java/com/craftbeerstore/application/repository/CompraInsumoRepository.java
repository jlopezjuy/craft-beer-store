package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.CompraInsumo;
import com.craftbeerstore.application.domain.Empresa;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompraInsumo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompraInsumoRepository extends JpaRepository<CompraInsumo, Long> {

    Page<CompraInsumo> findAllByEmpresa(Pageable pageable, Empresa empresa);

}
