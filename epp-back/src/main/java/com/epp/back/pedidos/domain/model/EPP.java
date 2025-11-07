package com.epp.back.pedidos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EPP {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String tipo;
    private BigDecimal precioUnitario;
    private Integer stockDisponible;
    private Boolean activo;
}

