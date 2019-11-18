package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Lote;
import com.craftbeerstore.application.domain.MedicionLote;
import com.craftbeerstore.application.domain.enumeration.TipoMedicion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;


/**
 * Spring Data  repository for the MedicionLote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicionLoteRepository extends JpaRepository<MedicionLote, Long> {

  Page<MedicionLote> findAllByLoteOrderByFechaRealizadoAsc(Pageable pageable, Lote lote);

  Page<MedicionLote> findAllByLoteAndTipoMedicionOrderByFechaRealizadoDesc(Pageable pageable, Lote lote, TipoMedicion tipoMedicion);

  @Query(value = "SELECT m FROM MedicionLote m  WHERE m.fechaRealizado >= ?1 AND m.fechaRealizado <= ?2 " +
    "AND m.tipoMedicion = ?3 " +
    "AND m.lote = ?4 " +
    "order by m.id, m.fechaRealizado")
  List<MedicionLote> queryMediciones(Instant from, Instant to,TipoMedicion tipoMedicion, Lote lote);

  List<MedicionLote> findAllByLoteAndFechaRealizadoGreaterThanEqualAndFechaRealizadoLessThanEqualAndTipoMedicion(Lote lote, Instant fechaFrom, Instant fechaTo, TipoMedicion tipoMedicion);
}
