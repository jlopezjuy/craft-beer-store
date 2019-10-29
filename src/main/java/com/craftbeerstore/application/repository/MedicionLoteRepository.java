package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Lote;
import com.craftbeerstore.application.domain.MedicionLote;
import com.craftbeerstore.application.domain.enumeration.TipoMedicion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicionLote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicionLoteRepository extends JpaRepository<MedicionLote, Long> {

  Page<MedicionLote> findAllByLoteOrderByFechaRealizadoAsc(Pageable pageable, Lote lote);

  Page<MedicionLote> findAllByLoteAndTipoMedicionOrderByFechaRealizadoDesc(Pageable pageable, Lote lote, TipoMedicion tipoMedicion);

}
