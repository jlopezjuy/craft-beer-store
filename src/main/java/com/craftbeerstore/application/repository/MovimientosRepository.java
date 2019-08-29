package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.Movimientos;

import java.math.BigDecimal;
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
     * @param
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
        + "        ANY_VALUE(prod.nombre_comercial), ANY_VALUE(prod.srm_color) as color "
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

    @Query(value = "SELECT  " +
      "sum(m.litros_totales) as litros " +
      "FROM " +
      "craftBeerStore.movimientos m, craftBeerStore.detalle_movimiento detmov, " +
      "craftBeerStore.presentacion p, craftBeerStore.producto prod " +
      "WHERE " +
      "m.fecha_movimiento >= :dateFrom " +
      "AND m.fecha_movimiento <= :dateTo " +
      "and detmov.movimientos_id = m.id " +
      "and detmov.presentacion_id = p.id " +
      "and prod.id = p.producto_id " +
      "and m.tipo_movimiento = 'VENTA' " +
      "AND m.empresa_id = :empresa ",
    nativeQuery = true)
    BigDecimal queryLitrosSemana(@Param("empresa")Long empresaId, @Param("dateFrom")LocalDate from, @Param("dateTo")LocalDate to);

    @Query(value = "SELECT CURDATE() - INTERVAL diffs.diff DAY AS periodo, " +
      "       COALESCE(SUM(litros_totales), 0) AS litros " +
      "FROM ( " +
      "    SELECT 0 as diff UNION ALL " +
      "    SELECT 1 UNION ALL " +
      "    SELECT 2 UNION ALL " +
      "    SELECT 3 UNION ALL " +
      "    SELECT 4 UNION ALL " +
      "    SELECT 6 UNION ALL " +
      "    SELECT 7 UNION ALL " +
      "    SELECT 8 UNION ALL " +
      "    SELECT 9 UNION ALL " +
      "    SELECT 10 UNION ALL " +
      "    SELECT 11 UNION ALL " +
      "    SELECT 12 UNION ALL " +
      "    SELECT 13 UNION ALL " +
      "    SELECT 14 UNION ALL " +
      "    SELECT 15 UNION ALL " +
      "    SELECT 16 UNION ALL " +
      "    SELECT 17 UNION ALL " +
      "    SELECT 18 UNION ALL " +
      "    SELECT 19 UNION ALL " +
      "    SELECT 20 UNION ALL " +
      "    SELECT 21 UNION ALL " +
      "    SELECT 22 UNION ALL " +
      "    SELECT 23 UNION ALL " +
      "    SELECT 24 UNION ALL " +
      "    SELECT 25 UNION ALL " +
      "    SELECT 26 UNION ALL " +
      "    SELECT 27 UNION ALL " +
      "    SELECT 28 UNION ALL " +
      "    SELECT 29 UNION ALL " +
      "    SELECT 30 " +
      ") diffs " +
      "LEFT JOIN craftBeerStore.movimientos m ON DATE(m.fecha_movimiento) = CURDATE() - INTERVAL diffs.diff DAY " +
      "AND tipo_movimiento = 'VENTA' " +
      "AND empresa_id = :empresaId " +
      "GROUP BY periodo", nativeQuery = true)
    List<Object[]> queryLitrosMes(@Param("empresa")Long empresaId);

    @Query(value = "SELECT CURDATE() - INTERVAL diffs.diff DAY AS periodo, " +
      "       COALESCE(SUM(litros_totales), 0) AS litros " +
      "FROM ( " +
      "    SELECT 0 as diff UNION ALL " +
      "    SELECT 1 UNION ALL " +
      "    SELECT 2 UNION ALL " +
      "    SELECT 3 UNION ALL " +
      "    SELECT 4 UNION ALL " +
      "    SELECT 6  " +
      ") diffs " +
      "LEFT JOIN craftBeerStore.movimientos m ON DATE(m.fecha_movimiento) = CURDATE() - INTERVAL diffs.diff DAY " +
      "AND tipo_movimiento = 'VENTA' " +
      "AND empresa_id = :empresaId " +
      "GROUP BY periodo;", nativeQuery = true)
    List<Object[]> queryLitrosSemana(@Param("empresa")Long empresaId);
}
