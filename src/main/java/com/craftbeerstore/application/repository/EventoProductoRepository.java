package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.EventoProducto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EventoProducto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventoProductoRepository extends JpaRepository<EventoProducto, Long> {

}
