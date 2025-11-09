package com.epp.back.pedidos.infrastructure.web.controller;

import com.epp.back.pedidos.application.dto.EmpresaResponse;
import com.epp.back.pedidos.application.mapper.PedidoMapper;
import com.epp.back.pedidos.application.service.ListarEmpresasService;
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
@RequestMapping("/api/v1/empresa")
public class EmpresaController {
    
    private final ListarEmpresasService listarEmpresasService;
    private final PedidoMapper pedidoMapper;
    
    @GetMapping("/list")
    public ResponseEntity<List<EmpresaResponse>> listarEmpresasDisponibles() {
        var empresas = listarEmpresasService.listarEmpresas();
        List<EmpresaResponse> response = pedidoMapper.toEmpresaResponseList(empresas);
        return ResponseEntity.ok(response);
    }
}

