package com.epp.back.pedidos.domain.port.in;

import com.epp.back.pedidos.domain.model.EPP;

import java.util.List;

public interface ListarEPPsUseCase {
    List<EPP> listarEPPs();
}

