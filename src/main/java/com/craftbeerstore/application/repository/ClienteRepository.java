package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.Cliente;
import com.craftbeerstore.application.domain.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Page<Cliente> findAllByEmpresa(Pageable pageable, Empresa empresa );
}
