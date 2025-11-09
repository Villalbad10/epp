package com.epp.back.pedidos.infrastructure.adapter;

import com.epp.back.pedidos.domain.model.ProductoQuimico;
import com.epp.back.pedidos.domain.port.out.ProductoQuimicoRepositoryPort;
import com.epp.back.pedidos.infrastructure.persistence.entity.ProductoQuimicoEntity;
import com.epp.back.pedidos.infrastructure.persistence.mapper.EntityMapper;
import com.epp.back.pedidos.infrastructure.persistence.repository.ProductoQuimicoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductoQuimicoRepositoryAdapter implements ProductoQuimicoRepositoryPort {
    
    private final ProductoQuimicoJpaRepository jpaRepository;
    private final EntityMapper mapper;
    
    @Override
    public List<ProductoQuimico> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(mapper::toProductoQuimicoDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<ProductoQuimico> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toProductoQuimicoDomain);
    }
    
    @Override
    public boolean existePorId(Long id) {
        return jpaRepository.existsById(id);
    }
}

