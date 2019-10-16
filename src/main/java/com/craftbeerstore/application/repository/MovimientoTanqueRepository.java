package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.MovimientoTanque;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MovimientoTanque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovimientoTanqueRepository extends JpaRepository<MovimientoTanque, Long> {

}
