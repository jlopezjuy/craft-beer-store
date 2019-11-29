package com.craftbeerstore.application.repository;
import com.craftbeerstore.application.domain.Barril;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Barril entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BarrilRepository extends JpaRepository<Barril, Long> {

}
