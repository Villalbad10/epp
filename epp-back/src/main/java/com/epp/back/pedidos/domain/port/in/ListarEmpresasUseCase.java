package com.epp.back.pedidos.domain.port.in;

import com.epp.back.pedidos.domain.model.Empresa;

import java.util.List;

public interface ListarEmpresasUseCase {
    List<Empresa> listarEmpresas();
}

