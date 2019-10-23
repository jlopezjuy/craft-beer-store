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

  Page<MedicionLote> findAllByLote(Pageable pageable, Lote lote);

  Page<MedicionLote> findAllByLoteAndTipoMedicion(Pageable pageable, Lote lote, TipoMedicion tipoMedicion);

}
