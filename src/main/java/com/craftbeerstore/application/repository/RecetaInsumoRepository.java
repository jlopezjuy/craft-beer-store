package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Receta;
import com.craftbeerstore.application.domain.RecetaInsumo;
import com.craftbeerstore.application.domain.enumeration.TipoInsumo;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RecetaInsumo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecetaInsumoRepository extends JpaRepository<RecetaInsumo, Long> {

    List<RecetaInsumo> findAllByRecetaAndTipoInsumo(Receta receta, TipoInsumo tipoInsumo);

    List<RecetaInsumo> findAllByRecetaAndTipoInsumoNotIn(Receta receta, List<TipoInsumo> tipoInsumos);

    void deleteByIdIn(List<Long> id);
}
