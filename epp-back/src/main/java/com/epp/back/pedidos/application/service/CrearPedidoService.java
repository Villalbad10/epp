package com.epp.back.pedidos.application.service;

import com.epp.back.pedidos.domain.exception.RecursoNoEncontradoException;
import com.epp.back.pedidos.domain.exception.ValidacionException;
import com.epp.back.pedidos.domain.model.*;
import com.epp.back.pedidos.domain.port.in.CrearPedidoUseCase;
import com.epp.back.pedidos.domain.port.out.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CrearPedidoService implements CrearPedidoUseCase {
    
    private final PedidoRepositoryPort pedidoRepository;
    private final EPPRepositoryPort eppRepository;
    private final EmpresaRepositoryPort empresaRepository;
    private final AreaRepositoryPort areaRepository;
    private final ProductoQuimicoRepositoryPort productoQuimicoRepository;
    
    @Override
    @Transactional
    public Pedido crearPedido(Pedido pedido) {
        // Validar empresa
        Empresa empresa = empresaRepository.buscarPorId(pedido.getEmpresa().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada con ID: " + pedido.getEmpresa().getId()));
        
        // Validar área
        Area area = areaRepository.buscarPorId(pedido.getArea().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Área no encontrada con ID: " + pedido.getArea().getId()));
        
        // Validar producto químico
        ProductoQuimico productoQuimico = productoQuimicoRepository.buscarPorId(pedido.getProductoQuimico().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto químico no encontrado con ID: " + pedido.getProductoQuimico().getId()));
        
        // Validar items
        if (pedido.getItems() == null || pedido.getItems().isEmpty()) {
            throw new ValidacionException("El pedido debe contener al menos un item");
        }
        
        // Validar y procesar items
        List<ItemPedido> itemsProcesados = new ArrayList<>();
        for (ItemPedido item : pedido.getItems()) {
            EPP epp = eppRepository.buscarPorId(item.getEpp().getId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("EPP no encontrado con ID: " + item.getEpp().getId()));
            
            if (!epp.getActivo()) {
                throw new ValidacionException("El EPP con ID " + epp.getId() + " no está activo");
            }
            
            if (item.getCantidad() <= 0) {
                throw new ValidacionException("La cantidad debe ser mayor a cero");
            }
            
            if (epp.getStockDisponible() < item.getCantidad()) {
                throw new ValidacionException("Stock insuficiente para el EPP: " + epp.getNombre() + ". Stock disponible: " + epp.getStockDisponible());
            }
            
            // Crear nuevo item con precio unitario y calcular subtotal
            ItemPedido itemProcesado = ItemPedido.builder()
                    .epp(epp)
                    .cantidad(item.getCantidad())
                    .precioUnitario(epp.getPrecioUnitario())
                    .build();
            itemProcesado.calcularSubtotal();
            itemsProcesados.add(itemProcesado);
        }
        
        // Generar número de pedido único
        String numeroPedido = "PED-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        // Crear pedido
        Pedido nuevoPedido = Pedido.builder()
                .numeroPedido(numeroPedido)
                .empresa(empresa)
                .area(area)
                .productoQuimico(productoQuimico)
                .fechaPedido(LocalDateTime.now())
                .estado("PENDIENTE")
                .observaciones(pedido.getObservaciones())
                .items(itemsProcesados)
                .build();
        
        nuevoPedido.calcularTotal();
        
        return pedidoRepository.guardar(nuevoPedido);
    }
}

