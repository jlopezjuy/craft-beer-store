package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Empresa;
import com.craftbeerstore.application.domain.Evento;
import java.util.stream.DoubleStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Evento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    Page<Evento> findAllByEmpresa(Pageable pageable, Empresa empresa);
}
