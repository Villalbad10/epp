package com.epp.back.pedidos.infrastructure.adapter;

import com.epp.back.pedidos.domain.model.EPP;
import com.epp.back.pedidos.domain.port.out.EPPRepositoryPort;
import com.epp.back.pedidos.infrastructure.persistence.entity.EPPEntity;
import com.epp.back.pedidos.infrastructure.persistence.mapper.EntityMapper;
import com.epp.back.pedidos.infrastructure.persistence.repository.EPPJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EPPRepositoryAdapter implements EPPRepositoryPort {
    
    private final EPPJpaRepository jpaRepository;
    private final EntityMapper mapper;
    
    @Override
    public List<EPP> listarTodos() {
        return jpaRepository.findByActivoTrue().stream()
                .map(mapper::toEPPDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<EPP> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toEPPDomain);
    }
    
    @Override
    public boolean existePorId(Long id) {
        return jpaRepository.existsById(id);
    }
}

