package com.epp.back.pedidos.domain.port.in;

import com.epp.back.pedidos.domain.model.Pedido;

public interface CrearPedidoUseCase {
    Pedido crearPedido(Pedido pedido);
}

