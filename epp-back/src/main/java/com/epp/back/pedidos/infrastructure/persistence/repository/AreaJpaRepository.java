package com.epp.back.pedidos.infrastructure.persistence.repository;

import com.epp.back.pedidos.infrastructure.persistence.entity.AreaEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaJpaRepository extends JpaRepository<AreaEntity, Long> {
    
    @EntityGraph(attributePaths = {"empresa"})
    Optional<AreaEntity> findById(Long id);
}

