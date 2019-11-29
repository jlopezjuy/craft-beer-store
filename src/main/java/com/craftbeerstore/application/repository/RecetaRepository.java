package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.Receta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Receta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

}
