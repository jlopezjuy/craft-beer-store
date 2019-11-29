package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.CompraInsumoDetalle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompraInsumoDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompraInsumoDetalleRepository extends JpaRepository<CompraInsumoDetalle, Long> {

}
