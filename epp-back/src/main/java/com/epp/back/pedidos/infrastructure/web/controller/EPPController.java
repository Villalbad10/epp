package com.epp.back.pedidos.infrastructure.web.controller;

import com.epp.back.pedidos.application.dto.EPPResponse;
import com.epp.back.pedidos.application.mapper.PedidoMapper;
import com.epp.back.pedidos.application.service.ListarEPPsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/epp")
@RequiredArgsConstructor
public class EPPController {
    
    private final ListarEPPsService listarEPPsService;
    private final PedidoMapper pedidoMapper;
    
    @GetMapping
    public ResponseEntity<List<EPPResponse>> listarEPPs() {
        var epps = listarEPPsService.listarEPPs();
        List<EPPResponse> response = pedidoMapper.toEPPResponseList(epps);
        return ResponseEntity.ok(response);
    }
}

