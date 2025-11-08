package com.epp.back.pedidos.infrastructure.persistence.repository;

import com.epp.back.pedidos.infrastructure.persistence.entity.EPPEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EPPJpaRepository extends JpaRepository<EPPEntity, Long> {
    List<EPPEntity> findByActivoTrue();
}

