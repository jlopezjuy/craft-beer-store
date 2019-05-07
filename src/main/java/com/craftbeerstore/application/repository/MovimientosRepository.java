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
        + "WHERE fecha_movimiento >= curdate() - INTERVAL DAYOFWEEK(curdate())+5 DAY "
        + "AND fecha_movimiento < curdate() - INTERVAL DAYOFWEEK(curdate())-2 DAY "
        + "AND empresa_id = :empresa "
        + "group by fecha_movimiento", nativeQuery = true)
    List<Object[]> queryMovimientoSemana(@Param("empresa")Long empresaId);

    @Query(value = "SELECT m.id, m.tipo_movimiento, m.fecha_movimiento, sum(m.precio_total) as total , sum(detmov.cantidad) as cantidad, "
        + "        prod.nombre_producto "
        + "    FROM "
        + "        craftbeerstore.movimientos m, craftbeerstore.detalle_movimiento detmov, "
        + "        craftbeerstore.presentacion p, craftbeerstore.producto prod "
        + "    WHERE "
        + "        m.fecha_movimiento >= curdate() - INTERVAL DAYOFWEEK(curdate())+5 DAY "
        + "        AND m.fecha_movimiento < curdate() - INTERVAL DAYOFWEEK(curdate())-2 DAY "
        + "        and detmov.movimientos_id = m.id "
        + "        and detmov.presentacion_id = p.id "
        + "        and prod.id = p.producto_id "
        + "        and m.tipo_movimiento = 'VENTA' "
        + "        AND m.empresa_id = :empresa "
        + "        group by prod.nombre_producto", nativeQuery = true)
    List<Object[]> queryVentaProductoSemana(@Param("empresa")Long empresaId);
}
