package com.epp.back.pedidos.infrastructure.web.controller;

import com.epp.back.pedidos.application.dto.AreaResponse;
import com.epp.back.pedidos.application.mapper.PedidoMapper;
import com.epp.back.pedidos.application.service.ListarAreasService;
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
@RequestMapping("/api/v1/area")
public class AreaController {
    
    private final ListarAreasService listarAreasService;
    private final PedidoMapper pedidoMapper;
    
    @GetMapping("/list")
    public ResponseEntity<List<AreaResponse>> listarAreasDisponibles() {
        var areas = listarAreasService.listarAreas();
        List<AreaResponse> response = pedidoMapper.toAreaResponseList(areas);
        return ResponseEntity.ok(response);
    }
}

