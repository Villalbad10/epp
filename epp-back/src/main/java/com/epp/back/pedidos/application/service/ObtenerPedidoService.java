package com.epp.back.pedidos.application.service;

import com.epp.back.pedidos.domain.model.Pedido;
import com.epp.back.pedidos.domain.port.in.ObtenerPedidoUseCase;
import com.epp.back.pedidos.domain.port.out.PedidoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ObtenerPedidoService implements ObtenerPedidoUseCase {
    
    private final PedidoRepositoryPort pedidoRepository;
    
    @Override
    public Optional<Pedido> obtenerPedido(Long id) {
        return pedidoRepository.buscarPorId(id);
    }
}

