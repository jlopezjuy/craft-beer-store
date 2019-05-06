package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.Movimientos;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Movimientos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovimientosRepository extends JpaRepository<Movimientos, Long> {

    /**
     *
     * @param pageable
     * @param empresa
     * @return
     */
    Page<Movimientos> findAllByEmpresa(Pageable pageable, Empresa empresa);

    @Query(value = "SELECT id, tipo_movimiento, fecha_movimiento, sum(precio_total) as total FROM craftbeerstore.movimientos "
        + "WHERE fecha_movimiento >= curdate() - INTERVAL DAYOFWEEK(curdate())+6 DAY "
        + "AND fecha_movimiento < curdate() - INTERVAL DAYOFWEEK(curdate())-1 DAY "
        + "AND empresa_id = :empresa "
        + "group by fecha_movimiento", nativeQuery = true)
    List<Object[]> queryMovimientoSemana(@Param("empresa")Long empresaId);
}
