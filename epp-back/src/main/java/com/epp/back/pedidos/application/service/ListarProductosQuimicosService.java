package com.epp.back.pedidos.application.service;

import com.epp.back.pedidos.domain.model.ProductoQuimico;
import com.epp.back.pedidos.domain.port.in.ListarProductosQuimicosUseCase;
import com.epp.back.pedidos.domain.port.out.ProductoQuimicoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarProductosQuimicosService implements ListarProductosQuimicosUseCase {
    
    private final ProductoQuimicoRepositoryPort productoQuimicoRepository;
    
    @Override
    public List<ProductoQuimico> listarProductosQuimicos() {
        return productoQuimicoRepository.listarTodos();
    }
}

