package com.epp.back.pedidos.domain.port.in;

import com.epp.back.pedidos.domain.model.ProductoQuimico;

import java.util.List;

public interface ListarProductosQuimicosUseCase {
    List<ProductoQuimico> listarProductosQuimicos();
}

