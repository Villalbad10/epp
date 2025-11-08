package com.epp.back.pedidos.domain.port.out;

import com.epp.back.pedidos.domain.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PedidoRepositoryPort {
    Pedido guardar(Pedido pedido);
    Optional<Pedido> buscarPorId(Long id);
    Page<Pedido> listarTodos(Pageable pageable);
    Optional<Pedido> buscarPorNumeroPedido(String numeroPedido);
}

