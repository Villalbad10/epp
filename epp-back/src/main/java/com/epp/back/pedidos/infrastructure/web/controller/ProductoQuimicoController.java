package com.epp.back.pedidos.infrastructure.web.controller;

import com.epp.back.pedidos.application.dto.ProductoQuimicoResponse;
import com.epp.back.pedidos.application.mapper.PedidoMapper;
import com.epp.back.pedidos.application.service.ListarProductosQuimicosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/producto-quimico")
public class ProductoQuimicoController {
    
    private final ListarProductosQuimicosService listarProductosQuimicosService;
    private final PedidoMapper pedidoMapper;
    
    @GetMapping("/list")
    public ResponseEntity<List<ProductoQuimicoResponse>> listarProductosQuimicosDisponibles() {
        var productosQuimicos = listarProductosQuimicosService.listarProductosQuimicos();
        List<ProductoQuimicoResponse> response = pedidoMapper.toProductoQuimicoResponseList(productosQuimicos);
        return ResponseEntity.ok(response);
    }
}

