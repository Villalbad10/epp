package com.epp.back.pedidos.domain.port.out;

import com.epp.back.pedidos.domain.model.Empresa;

import java.util.Optional;

public interface EmpresaRepositoryPort {
    Optional<Empresa> buscarPorId(Long id);
    boolean existePorId(Long id);
}

