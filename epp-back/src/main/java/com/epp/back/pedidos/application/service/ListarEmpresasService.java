package com.epp.back.pedidos.application.service;

import com.epp.back.pedidos.domain.model.Empresa;
import com.epp.back.pedidos.domain.port.in.ListarEmpresasUseCase;
import com.epp.back.pedidos.domain.port.out.EmpresaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarEmpresasService implements ListarEmpresasUseCase {
    
    private final EmpresaRepositoryPort empresaRepository;
    
    @Override
    public List<Empresa> listarEmpresas() {
        return empresaRepository.listarTodos();
    }
}

