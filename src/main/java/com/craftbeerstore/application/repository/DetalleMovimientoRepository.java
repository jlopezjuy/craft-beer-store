package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.DetalleMovimiento;
import com.craftbeerstore.application.domain.Movimientos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DetalleMovimiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetalleMovimientoRepository extends JpaRepository<DetalleMovimiento, Long> {

    /**
     *
     * @param pageable
     * @param movimientos
     * @return
     */
    Page<DetalleMovimiento> findAllByMovimientos(Pageable pageable, Movimientos movimientos);
}
