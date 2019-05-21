package com.craftbeerstore.application.repository;

import com.craftbeerstore.application.domain.Cliente;
import com.craftbeerstore.application.domain.PuntoDeVenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PuntoDeVenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PuntoDeVentaRepository extends JpaRepository<PuntoDeVenta, Long> {


    Page<PuntoDeVenta> findAllByCliente(Pageable pageable, Cliente cliente);
}
