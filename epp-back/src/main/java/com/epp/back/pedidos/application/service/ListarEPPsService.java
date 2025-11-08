package com.epp.back.pedidos.application.service;

import com.epp.back.pedidos.domain.model.EPP;
import com.epp.back.pedidos.domain.port.in.ListarEPPsUseCase;
import com.epp.back.pedidos.domain.port.out.EPPRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarEPPsService implements ListarEPPsUseCase {
    
    private final EPPRepositoryPort eppRepository;
    
    @Override
    public List<EPP> listarEPPs() {
        return eppRepository.listarTodos();
    }
}

