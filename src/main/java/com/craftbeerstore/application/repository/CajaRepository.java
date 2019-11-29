package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Caja;
import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.enumeration.TipoMovimientoCaja;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Caja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CajaRepository extends JpaRepository<Caja, Long> {

    Page<Caja> findAllByEmpresa(Pageable pageable, Empresa empresa);

    @Query("SELECT SUM(c.importe) FROM Caja c where c.empresa = :empresa and c.tipoMovimiento = :tipoMovimiento")
    BigDecimal sumIngreso(@Param("empresa")Empresa empresa, @Param("tipoMovimiento") TipoMovimientoCaja tipoMovimiento);

    @Query(value = "SELECT CURDATE() - INTERVAL diffs.diff DAY AS periodo, " +
        "       COALESCE(SUM(importe), 0) AS total " +
        "FROM ( " +
        "    SELECT 0 as diff UNION ALL " +
        "    SELECT 1 UNION ALL " +
        "    SELECT 2 UNION ALL " +
        "    SELECT 3 UNION ALL " +
        "    SELECT 4 UNION ALL " +
        "    SELECT 5 UNION ALL " +
        "    SELECT 6 " +
        ") diffs " +
        "LEFT JOIN craftBeerStore.caja c ON DATE(c.fecha) = CURDATE() - INTERVAL diffs.diff DAY " +
        "AND tipo_movimiento = 'INGRESO' " +
        "AND empresa_id = :empresaId " +
        "GROUP BY periodo", nativeQuery = true)
    List<Object[]> getSemanaIngresos(@Param("empresaId")Long empresaId);

  @Query(value = "SELECT CURDATE() - INTERVAL diffs.diff DAY AS periodo, " +
    "       COALESCE(SUM(importe), 0) AS total " +
    "FROM ( " +
    "    SELECT 0 as diff UNION ALL " +
    "    SELECT 1 UNION ALL " +
    "    SELECT 2 UNION ALL " +
    "    SELECT 3 UNION ALL " +
    "    SELECT 4 UNION ALL " +
    "    SELECT 5 UNION ALL " +
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
    "LEFT JOIN craftBeerStore.caja c ON DATE(c.fecha) = CURDATE() - INTERVAL diffs.diff DAY " +
    "AND tipo_movimiento = 'INGRESO' " +
    "AND empresa_id = :empresaId " +
    "GROUP BY periodo", nativeQuery = true)
  List<Object[]> getMesIngresos(@Param("empresaId")Long empresaId);
}
