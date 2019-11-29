package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.CompraInsumo;
import com.craftbeerstore.application.domain.CompraInsumoDetalle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the CompraInsumoDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompraInsumoDetalleRepository extends JpaRepository<CompraInsumoDetalle, Long> {

    List<CompraInsumoDetalle> findAllByCompraInsumo(CompraInsumo compraInsumo);
}
