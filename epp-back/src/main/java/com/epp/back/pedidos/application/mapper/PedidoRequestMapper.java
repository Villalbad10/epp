package com.epp.back.pedidos.application.mapper;

import com.epp.back.pedidos.application.dto.ItemPedidoRequest;
import com.epp.back.pedidos.application.dto.PedidoRequest;
import com.epp.back.pedidos.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PedidoRequestMapper {
    
    public Pedido toDomain(PedidoRequest request) {
        if (request == null) {
            return null;
        }
        
        Empresa empresa = Empresa.builder()
                .id(request.getEmpresaId())
                .build();
        
        Area area = Area.builder()
                .id(request.getAreaId())
                .build();
        
        ProductoQuimico productoQuimico = ProductoQuimico.builder()
                .id(request.getProductoQuimicoId())
                .build();
        
        List<ItemPedido> items = request.getItems().stream()
                .map(this::toItemPedidoDomain)
                .collect(Collectors.toList());
        
        return Pedido.builder()
                .empresa(empresa)
                .area(area)
                .productoQuimico(productoQuimico)
                .observaciones(request.getObservaciones())
                .items(items)
                .build();
    }
    
    private ItemPedido toItemPedidoDomain(ItemPedidoRequest request) {
        if (request == null) {
            return null;
        }
        
        EPP epp = EPP.builder()
                .id(request.getEppId())
                .build();
        
        return ItemPedido.builder()
                .epp(epp)
                .cantidad(request.getCantidad())
                .build();
    }
}

