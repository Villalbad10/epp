package com.epp.back.pedidos.infrastructure.adapter;

import com.epp.back.pedidos.domain.model.Pedido;
import com.epp.back.pedidos.domain.port.out.PedidoRepositoryPort;
import com.epp.back.pedidos.infrastructure.persistence.entity.PedidoEntity;
import com.epp.back.pedidos.infrastructure.persistence.mapper.EntityMapper;
import com.epp.back.pedidos.infrastructure.persistence.repository.PedidoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PedidoRepositoryAdapter implements PedidoRepositoryPort {
    
    private final PedidoJpaRepository jpaRepository;
    private final EntityMapper mapper;
    
    @Override
    public Pedido guardar(Pedido pedido) {
        PedidoEntity entity = mapper.toEntity(pedido);
        PedidoEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public Page<Pedido> listarTodos(Pageable pageable) {
        Page<PedidoEntity> entities = jpaRepository.findAll(pageable);
        return entities.map(mapper::toDomain);
    }
    
    @Override
    public Optional<Pedido> buscarPorNumeroPedido(String numeroPedido) {
        return jpaRepository.findByNumeroPedido(numeroPedido)
                .map(mapper::toDomain);
    }
}

