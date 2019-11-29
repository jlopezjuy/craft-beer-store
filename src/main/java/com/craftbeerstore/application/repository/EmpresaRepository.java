package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Empresa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Query("select empresa from Empresa empresa where empresa.user.login = ?#{principal.username}")
    Page<Empresa> findByUserIsCurrentUser(Pageable pageable);

    @Query("select empresa from Empresa empresa where empresa.user.login = ?#{principal.username}")
    Optional<Empresa> findByUserIsCurrentUser();

    Optional<Empresa> findByCorreo(String email);
}
