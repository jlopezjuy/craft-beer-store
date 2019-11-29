package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.Presentacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Presentacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PresentacionRepository extends JpaRepository<Presentacion, Long> {

}
