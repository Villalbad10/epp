package com.epp.back.pedidos.infrastructure.persistence.repository;

import com.epp.back.pedidos.infrastructure.persistence.entity.ProductoQuimicoEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoQuimicoJpaRepository extends JpaRepository<ProductoQuimicoEntity, Long> {
    
    @EntityGraph(attributePaths = {"empresa"})
    Optional<ProductoQuimicoEntity> findById(Long id);
}

