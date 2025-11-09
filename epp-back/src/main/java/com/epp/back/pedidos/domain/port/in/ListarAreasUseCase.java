package com.epp.back.pedidos.domain.port.in;

import com.epp.back.pedidos.domain.model.Area;

import java.util.List;

public interface ListarAreasUseCase {
    List<Area> listarAreas();
}

