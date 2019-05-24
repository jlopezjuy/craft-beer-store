package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.Insumo;
import com.craftbeerstore.application.domain.enumeration.TipoInsumo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Insumo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {

    Page<Insumo> findAllByEmpresa(Pageable pageable, Empresa empresa );

    List<Insumo> findAllByEmpresaAndTipo(Empresa empresa, TipoInsumo tipoInsumo);
}
