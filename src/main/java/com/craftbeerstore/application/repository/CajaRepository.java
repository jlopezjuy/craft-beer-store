package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Caja;
import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.enumeration.TipoMovimientoCaja;
import java.math.BigDecimal;
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
}
