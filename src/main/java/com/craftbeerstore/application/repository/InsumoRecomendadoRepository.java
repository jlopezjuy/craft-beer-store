package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.InsumoRecomendado;
import com.craftbeerstore.application.domain.enumeration.TipoInsumo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the InsumoRecomendado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsumoRecomendadoRepository extends JpaRepository<InsumoRecomendado, Long> {

  List<InsumoRecomendado> findAllByTipo( TipoInsumo tipoInsumo);

  List<InsumoRecomendado> findAllByTipoNotIn( List<TipoInsumo> tipoInsumos);

}
