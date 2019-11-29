package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.RecetaInsumo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RecetaInsumo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecetaInsumoRepository extends JpaRepository<RecetaInsumo, Long> {

}
