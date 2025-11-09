package com.epp.back.pedidos.infrastructure.adapter;

import com.epp.back.pedidos.domain.model.Area;
import com.epp.back.pedidos.domain.port.out.AreaRepositoryPort;
import com.epp.back.pedidos.infrastructure.persistence.entity.AreaEntity;
import com.epp.back.pedidos.infrastructure.persistence.mapper.EntityMapper;
import com.epp.back.pedidos.infrastructure.persistence.repository.AreaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AreaRepositoryAdapter implements AreaRepositoryPort {
    
    private final AreaJpaRepository jpaRepository;
    private final EntityMapper mapper;
    
    @Override
    public List<Area> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(mapper::toAreaDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Area> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toAreaDomain);
    }
    
    @Override
    public boolean existePorId(Long id) {
        return jpaRepository.existsById(id);
    }
}

