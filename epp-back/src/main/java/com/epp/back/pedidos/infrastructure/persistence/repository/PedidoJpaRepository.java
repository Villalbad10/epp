package com.epp.back.pedidos.infrastructure.persistence.repository;

import com.epp.back.pedidos.infrastructure.persistence.entity.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoJpaRepository extends JpaRepository<PedidoEntity, Long> {
    
    @EntityGraph(attributePaths = {"empresa", "area", "productoQuimico", "items", "items.epp"})
    Optional<PedidoEntity> findById(Long id);
    
    @EntityGraph(attributePaths = {"empresa", "area", "productoQuimico", "items", "items.epp"})
    Optional<PedidoEntity> findByNumeroPedido(String numeroPedido);
    
    @EntityGraph(attributePaths = {"empresa", "area", "productoQuimico", "items", "items.epp"})
    Page<PedidoEntity> findAll(Pageable pageable);
}

