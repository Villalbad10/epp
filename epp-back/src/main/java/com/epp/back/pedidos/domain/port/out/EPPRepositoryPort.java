package com.epp.back.pedidos.domain.port.out;

import com.epp.back.pedidos.domain.model.EPP;

import java.util.List;
import java.util.Optional;

public interface EPPRepositoryPort {
    List<EPP> listarTodos();
    Optional<EPP> buscarPorId(Long id);
    boolean existePorId(Long id);
}

