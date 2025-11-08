package com.epp.back.pedidos.domain.port.out;

import com.epp.back.pedidos.domain.model.ProductoQuimico;

import java.util.Optional;

public interface ProductoQuimicoRepositoryPort {
    Optional<ProductoQuimico> buscarPorId(Long id);
    boolean existePorId(Long id);
}

