package com.epp.back.pedidos.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaResponse {
    private Long id;
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private String email;
}

