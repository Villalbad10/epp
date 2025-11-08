package com.epp.back.pedidos.infrastructure.adapter;

import com.epp.back.pedidos.domain.model.Empresa;
import com.epp.back.pedidos.domain.port.out.EmpresaRepositoryPort;
import com.epp.back.pedidos.infrastructure.persistence.entity.EmpresaEntity;
import com.epp.back.pedidos.infrastructure.persistence.mapper.EntityMapper;
import com.epp.back.pedidos.infrastructure.persistence.repository.EmpresaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmpresaRepositoryAdapter implements EmpresaRepositoryPort {
    
    private final EmpresaJpaRepository jpaRepository;
    private final EntityMapper mapper;
    
    @Override
    public Optional<Empresa> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toEmpresaDomain);
    }
    
    @Override
    public boolean existePorId(Long id) {
        return jpaRepository.existsById(id);
    }
}

