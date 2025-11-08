package com.epp.back.pedidos.domain.port.out;

import com.epp.back.pedidos.domain.model.Area;

import java.util.Optional;

public interface AreaRepositoryPort {
    Optional<Area> buscarPorId(Long id);
    boolean existePorId(Long id);
}

