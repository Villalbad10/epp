package com.epp.back.pedidos.application.mapper;

import com.epp.back.pedidos.application.dto.*;
import com.epp.back.pedidos.domain.model.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {
    
    public PedidoResponse toResponse(Pedido pedido) {
        if (pedido == null) {
            return null;
        }
        
        return PedidoResponse.builder()
                .id(pedido.getId())
                .numeroPedido(pedido.getNumeroPedido())
                .empresa(toEmpresaResponse(pedido.getEmpresa()))
                .area(toAreaResponse(pedido.getArea()))
                .productoQuimico(toProductoQuimicoResponse(pedido.getProductoQuimico()))
                .fechaPedido(pedido.getFechaPedido())
                .estado(pedido.getEstado())
                .observaciones(pedido.getObservaciones())
                .items(toItemPedidoResponseList(pedido.getItems()))
                .total(pedido.getTotal())
                .build();
    }
    
    public List<PedidoResponse> toResponseList(List<Pedido> pedidos) {
        if (pedidos == null) {
            return List.of();
        }
        return pedidos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public PageResponse<PedidoResponse> toPageResponse(Page<Pedido> pedidoPage) {
        return PageResponse.<PedidoResponse>builder()
                .content(toResponseList(pedidoPage.getContent()))
                .page(pedidoPage.getNumber())
                .size(pedidoPage.getSize())
                .totalElements(pedidoPage.getTotalElements())
                .totalPages(pedidoPage.getTotalPages())
                .first(pedidoPage.isFirst())
                .last(pedidoPage.isLast())
                .build();
    }
    
    public ItemPedidoResponse toItemPedidoResponse(ItemPedido item) {
        if (item == null) {
            return null;
        }
        
        return ItemPedidoResponse.builder()
                .id(item.getId())
                .epp(toEPPResponse(item.getEpp()))
                .cantidad(item.getCantidad())
                .precioUnitario(item.getPrecioUnitario())
                .subtotal(item.getSubtotal())
                .build();
    }
    
    public List<ItemPedidoResponse> toItemPedidoResponseList(List<ItemPedido> items) {
        if (items == null) {
            return List.of();
        }
        return items.stream()
                .map(this::toItemPedidoResponse)
                .collect(Collectors.toList());
    }
    
    public EPPResponse toEPPResponse(EPP epp) {
        if (epp == null) {
            return null;
        }
        
        return EPPResponse.builder()
                .id(epp.getId())
                .codigo(epp.getCodigo())
                .nombre(epp.getNombre())
                .descripcion(epp.getDescripcion())
                .tipo(epp.getTipo())
                .precioUnitario(epp.getPrecioUnitario())
                .stockDisponible(epp.getStockDisponible())
                .activo(epp.getActivo())
                .build();
    }
    
    public List<EPPResponse> toEPPResponseList(List<EPP> epps) {
        if (epps == null) {
            return List.of();
        }
        return epps.stream()
                .map(this::toEPPResponse)
                .collect(Collectors.toList());
    }
    
    public EmpresaResponse toEmpresaResponse(Empresa empresa) {
        if (empresa == null) {
            return null;
        }
        
        return EmpresaResponse.builder()
                .id(empresa.getId())
                .nombre(empresa.getNombre())
                .nit(empresa.getNit())
                .direccion(empresa.getDireccion())
                .telefono(empresa.getTelefono())
                .email(empresa.getEmail())
                .build();
    }
    
    public AreaResponse toAreaResponse(Area area) {
        if (area == null) {
            return null;
        }
        
        return AreaResponse.builder()
                .id(area.getId())
                .nombre(area.getNombre())
                .descripcion(area.getDescripcion())
                .build();
    }
    
    public ProductoQuimicoResponse toProductoQuimicoResponse(ProductoQuimico productoQuimico) {
        if (productoQuimico == null) {
            return null;
        }
        
        return ProductoQuimicoResponse.builder()
                .id(productoQuimico.getId())
                .nombre(productoQuimico.getNombre())
                .codigo(productoQuimico.getCodigo())
                .descripcion(productoQuimico.getDescripcion())
                .build();
    }
    
    public List<EmpresaResponse> toEmpresaResponseList(List<Empresa> empresas) {
        if (empresas == null) {
            return List.of();
        }
        return empresas.stream()
                .map(this::toEmpresaResponse)
                .collect(Collectors.toList());
    }
    
    public List<AreaResponse> toAreaResponseList(List<Area> areas) {
        if (areas == null) {
            return List.of();
        }
        return areas.stream()
                .map(this::toAreaResponse)
                .collect(Collectors.toList());
    }
    
    public List<ProductoQuimicoResponse> toProductoQuimicoResponseList(List<ProductoQuimico> productosQuimicos) {
        if (productosQuimicos == null) {
            return List.of();
        }
        return productosQuimicos.stream()
                .map(this::toProductoQuimicoResponse)
                .collect(Collectors.toList());
    }
}

