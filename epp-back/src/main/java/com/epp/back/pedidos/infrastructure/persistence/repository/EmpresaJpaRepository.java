package com.epp.back.pedidos.infrastructure.persistence.repository;

import com.epp.back.pedidos.infrastructure.persistence.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaJpaRepository extends JpaRepository<EmpresaEntity, Long> {
}

