package com.epp.back.pedidos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    private Long id;
    private String nombre;
    private String descripcion;
    private Empresa empresa;
}

