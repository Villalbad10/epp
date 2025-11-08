package com.epp.back.pedidos.infrastructure.web.controller;

import com.epp.back.pedidos.application.dto.PageResponse;
import com.epp.back.pedidos.application.dto.PedidoRequest;
import com.epp.back.pedidos.application.dto.PedidoResponse;
import com.epp.back.pedidos.application.mapper.PedidoMapper;
import com.epp.back.pedidos.application.mapper.PedidoRequestMapper;
import com.epp.back.pedidos.application.service.CrearPedidoService;
import com.epp.back.pedidos.application.service.ListarPedidosService;
import com.epp.back.pedidos.application.service.ObtenerPedidoService;
import com.epp.back.pedidos.domain.exception.RecursoNoEncontradoException;
import com.epp.back.pedidos.domain.model.Pedido;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    
    private final CrearPedidoService crearPedidoService;
    private final ListarPedidosService listarPedidosService;
    private final ObtenerPedidoService obtenerPedidoService;
    private final PedidoMapper pedidoMapper;
    private final PedidoRequestMapper pedidoRequestMapper;
    
    @PostMapping
    public ResponseEntity<PedidoResponse> crearPedido(@Valid @RequestBody PedidoRequest request) {
        Pedido pedidoDomain = pedidoRequestMapper.toDomain(request);
        Pedido pedidoCreado = crearPedidoService.crearPedido(pedidoDomain);
        PedidoResponse response = pedidoMapper.toResponse(pedidoCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/list")
    public ResponseEntity<PageResponse<PedidoResponse>> listarPedidos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaPedido") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("ASC") ? 
                Sort.by(sortBy).ascending() : 
                Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        
        var pedidosPage = listarPedidosService.listarPedidos(pageable);
        PageResponse<PedidoResponse> response = pedidoMapper.toPageResponse(pedidosPage);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> obtenerPedido(@PathVariable Long id) {
        Pedido pedido = obtenerPedidoService.obtenerPedido(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id));
        
        PedidoResponse response = pedidoMapper.toResponse(pedido);
        return ResponseEntity.ok(response);
    }
}

