package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Evento;
import com.craftbeerstore.application.domain.EventoProducto;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EventoProducto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventoProductoRepository extends JpaRepository<EventoProducto, Long> {

    List<EventoProducto> findByEvento(Evento evento);

}
