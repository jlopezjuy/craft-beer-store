package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.EtapaLote;
import com.craftbeerstore.application.domain.Lote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.Optional;


/**
 * Spring Data  repository for the EtapaLote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtapaLoteRepository extends JpaRepository<EtapaLote, Long> {

  Page<EtapaLote> findAllByLoteOrderByIdDesc(Pageable pageable, Lote lote);

  Optional<EtapaLote> findTopByLoteOrderByIdDesc(Lote lote);
}
