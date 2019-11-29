package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.Equipamiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Equipamiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipamientoRepository extends JpaRepository<Equipamiento, Long> {

}
