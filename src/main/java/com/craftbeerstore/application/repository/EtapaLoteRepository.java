package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.EtapaLote;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EtapaLote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtapaLoteRepository extends JpaRepository<EtapaLote, Long> {

}
