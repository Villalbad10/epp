package com.epp.back.pedidos.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EPPResponse {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String tipo;
    private BigDecimal precioUnitario;
    private Integer stockDisponible;
    private Boolean activo;
}

