package com.epp.back.pedidos.domain.port.out;

import com.epp.back.pedidos.domain.model.Area;

import java.util.List;
import java.util.Optional;

public interface AreaRepositoryPort {
    List<Area> listarTodos();
    Optional<Area> buscarPorId(Long id);
    boolean existePorId(Long id);
}

