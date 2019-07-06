package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.Movimientos;
import java.time.LocalDate;
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
     * @param pageableMovimientosServiceImpl
     * @param empresa
     * @return
     */
    Page<Movimientos> findAllByEmpresa(Pageable pageable, Empresa empresa);

    @Query(value = "SELECT ANY_VALUE(id),ANY_VALUE(tipo_movimiento), ANY_VALUE(fecha_movimiento), sum(precio_total) as total FROM craftBeerStore.movimientos "
        + "WHERE fecha_movimiento >= :dateFrom "
        + "AND fecha_movimiento <= :dateTo "
        + "AND tipo_movimiento = 'VENTA' "
        + "AND empresa_id = :empresa "
        + "group by fecha_movimiento", nativeQuery = true)
    List<Object[]> queryMovimientoSemana(@Param("empresa")Long empresaId, @Param("dateFrom")LocalDate from, @Param("dateTo")LocalDate to);

    @Query(value = "SELECT ANY_VALUE(m.id), ANY_VALUE(m.tipo_movimiento), ANY_VALUE(m.fecha_movimiento), sum(m.precio_total) as total , sum(detmov.cantidad) as cantidad, "
        + "        ANY_VALUE(prod.nombre_comercial), ANY_VALUE(prod.id) as productoId "
        + "    FROM "
        + "        craftBeerStore.movimientos m, craftBeerStore.detalle_movimiento detmov, "
        + "        craftBeerStore.presentacion p, craftBeerStore.producto prod "
        + "    WHERE "
        + "        m.fecha_movimiento >= :dateFrom "
        + "        AND m.fecha_movimiento <= :dateTo "
        + "        and detmov.movimientos_id = m.id "
        + "        and detmov.presentacion_id = p.id "
        + "        and prod.id = p.producto_id "
        + "        and m.tipo_movimiento = 'VENTA' "
        + "        AND m.empresa_id = :empresa "
        + "        group by prod.nombre_comercial", nativeQuery = true)
    List<Object[]> queryVentaProductoSemana(@Param("empresa")Long empresaId, @Param("dateFrom")LocalDate from, @Param("dateTo")LocalDate to);
}
