package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.MedicionLote;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicionLote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicionLoteRepository extends JpaRepository<MedicionLote, Long> {

}
