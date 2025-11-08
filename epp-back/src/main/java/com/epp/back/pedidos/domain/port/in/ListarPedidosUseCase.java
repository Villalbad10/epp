package com.epp.back.pedidos.domain.port.in;

import com.epp.back.pedidos.domain.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarPedidosUseCase {
    Page<Pedido> listarPedidos(Pageable pageable);
}

