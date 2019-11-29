package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.Tanque;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tanque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TanqueRepository extends JpaRepository<Tanque, Long> {

}
