package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.PuntoDeVenta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PuntoDeVenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuntoDeVentaRepository extends JpaRepository<PuntoDeVenta, Long> {

}
