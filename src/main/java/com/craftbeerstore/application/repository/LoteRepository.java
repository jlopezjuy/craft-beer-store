package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.Lote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Lote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {
  Page<Lote> findAllByEmpresa(Pageable pageable, Empresa empresa );
}
