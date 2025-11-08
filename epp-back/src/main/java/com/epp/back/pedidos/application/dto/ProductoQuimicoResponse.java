package com.epp.back.pedidos.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoQuimicoResponse {
    private Long id;
    private String nombre;
    private String codigo;
    private String descripcion;
}

