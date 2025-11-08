package com.epp.back.pedidos.application.service;

import com.epp.back.pedidos.domain.model.Pedido;
import com.epp.back.pedidos.domain.port.in.ListarPedidosUseCase;
import com.epp.back.pedidos.domain.port.out.PedidoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListarPedidosService implements ListarPedidosUseCase {
    
    private final PedidoRepositoryPort pedidoRepository;
    
    @Override
    public Page<Pedido> listarPedidos(Pageable pageable) {
        return pedidoRepository.listarTodos(pageable);
    }
}

