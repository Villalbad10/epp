package com.epp.back.pedidos.application.service;

import com.epp.back.pedidos.domain.model.Area;
import com.epp.back.pedidos.domain.port.in.ListarAreasUseCase;
import com.epp.back.pedidos.domain.port.out.AreaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarAreasService implements ListarAreasUseCase {
    
    private final AreaRepositoryPort areaRepository;
    
    @Override
    public List<Area> listarAreas() {
        return areaRepository.listarTodos();
    }
}

