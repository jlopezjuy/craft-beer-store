package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.Estilos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Estilos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstilosRepository extends JpaRepository<Estilos, Long> {

    List<Estilos> findAllByOrderByNombreEstilo();

}
