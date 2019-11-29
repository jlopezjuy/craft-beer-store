package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.InsumoRecomendado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InsumoRecomendado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsumoRecomendadoRepository extends JpaRepository<InsumoRecomendado, Long> {

}
