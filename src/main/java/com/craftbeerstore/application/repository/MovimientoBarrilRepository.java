package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Barril;
import com.craftbeerstore.application.domain.MovimientoBarril;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MovimientoBarril entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovimientoBarrilRepository extends JpaRepository<MovimientoBarril, Long> {

  Page<MovimientoBarril> findAllByBarril(Pageable pageable, Barril barril);
}
