package com.epp.back.pedidos.infrastructure.persistence.mapper;

import com.epp.back.pedidos.domain.model.*;
import com.epp.back.pedidos.infrastructure.persistence.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper {
    
    // Domain to Entity
    public PedidoEntity toEntity(Pedido pedido) {
        if (pedido == null) {
            return null;
        }
        
        PedidoEntity entity = PedidoEntity.builder()
                .id(pedido.getId())
                .numeroPedido(pedido.getNumeroPedido())
                .empresa(toEmpresaEntity(pedido.getEmpresa()))
                .area(toAreaEntity(pedido.getArea()))
                .productoQuimico(toProductoQuimicoEntity(pedido.getProductoQuimico()))
                .fechaPedido(pedido.getFechaPedido())
                .estado(pedido.getEstado())
                .observaciones(pedido.getObservaciones())
                .total(pedido.getTotal())
                .build();
        
        if (pedido.getItems() != null) {
            List<ItemPedidoEntity> items = pedido.getItems().stream()
                    .map(item -> toItemPedidoEntity(item, entity))
                    .collect(Collectors.toList());
            entity.setItems(items);
        }
        
        return entity;
    }
    
    // Entity to Domain
    public Pedido toDomain(PedidoEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Pedido pedido = Pedido.builder()
                .id(entity.getId())
                .numeroPedido(entity.getNumeroPedido())
                .empresa(toEmpresaDomain(entity.getEmpresa()))
                .area(toAreaDomain(entity.getArea()))
                .productoQuimico(toProductoQuimicoDomain(entity.getProductoQuimico()))
                .fechaPedido(entity.getFechaPedido())
                .estado(entity.getEstado())
                .observaciones(entity.getObservaciones())
                .total(entity.getTotal())
                .build();
        
        if (entity.getItems() != null) {
            List<ItemPedido> items = entity.getItems().stream()
                    .map(this::toItemPedidoDomain)
                    .collect(Collectors.toList());
            pedido.setItems(items);
        }
        
        return pedido;
    }
    
    public EPPEntity toEPPEntity(EPP epp) {
        if (epp == null) {
            return null;
        }
        
        return EPPEntity.builder()
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
    
    public EPP toEPPDomain(EPPEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return EPP.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .tipo(entity.getTipo())
                .precioUnitario(entity.getPrecioUnitario())
                .stockDisponible(entity.getStockDisponible())
                .activo(entity.getActivo())
                .build();
    }
    
    public EmpresaEntity toEmpresaEntity(Empresa empresa) {
        if (empresa == null) {
            return null;
        }
        
        return EmpresaEntity.builder()
                .id(empresa.getId())
                .nombre(empresa.getNombre())
                .nit(empresa.getNit())
                .direccion(empresa.getDireccion())
                .telefono(empresa.getTelefono())
                .email(empresa.getEmail())
                .build();
    }
    
    public Empresa toEmpresaDomain(EmpresaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Empresa.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .nit(entity.getNit())
                .direccion(entity.getDireccion())
                .telefono(entity.getTelefono())
                .email(entity.getEmail())
                .build();
    }
    
    public AreaEntity toAreaEntity(Area area) {
        if (area == null) {
            return null;
        }
        
        return AreaEntity.builder()
                .id(area.getId())
                .nombre(area.getNombre())
                .descripcion(area.getDescripcion())
                .empresa(toEmpresaEntity(area.getEmpresa()))
                .build();
    }
    
    public Area toAreaDomain(AreaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Area.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .empresa(toEmpresaDomain(entity.getEmpresa()))
                .build();
    }
    
    public ProductoQuimicoEntity toProductoQuimicoEntity(ProductoQuimico productoQuimico) {
        if (productoQuimico == null) {
            return null;
        }
        
        return ProductoQuimicoEntity.builder()
                .id(productoQuimico.getId())
                .nombre(productoQuimico.getNombre())
                .codigo(productoQuimico.getCodigo())
                .descripcion(productoQuimico.getDescripcion())
                .empresa(toEmpresaEntity(productoQuimico.getEmpresa()))
                .build();
    }
    
    public ProductoQuimico toProductoQuimicoDomain(ProductoQuimicoEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return ProductoQuimico.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .codigo(entity.getCodigo())
                .descripcion(entity.getDescripcion())
                .empresa(toEmpresaDomain(entity.getEmpresa()))
                .build();
    }
    
    public ItemPedidoEntity toItemPedidoEntity(ItemPedido item, PedidoEntity pedidoEntity) {
        if (item == null) {
            return null;
        }
        
        return ItemPedidoEntity.builder()
                .id(item.getId())
                .pedido(pedidoEntity)
                .epp(toEPPEntity(item.getEpp()))
                .cantidad(item.getCantidad())
                .precioUnitario(item.getPrecioUnitario())
                .subtotal(item.getSubtotal())
                .build();
    }
    
    public ItemPedido toItemPedidoDomain(ItemPedidoEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return ItemPedido.builder()
                .id(entity.getId())
                .epp(toEPPDomain(entity.getEpp()))
                .cantidad(entity.getCantidad())
                .precioUnitario(entity.getPrecioUnitario())
                .subtotal(entity.getSubtotal())
                .build();
    }
}

