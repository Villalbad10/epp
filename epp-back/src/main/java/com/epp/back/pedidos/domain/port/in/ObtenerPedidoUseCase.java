package com.epp.back.pedidos.domain.port.in;

import com.epp.back.pedidos.domain.model.Pedido;

import java.util.Optional;

public interface ObtenerPedidoUseCase {
    Optional<Pedido> obtenerPedido(Long id);
}

