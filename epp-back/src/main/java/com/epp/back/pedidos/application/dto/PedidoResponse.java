package com.epp.back.pedidos.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponse {
    private Long id;
    private String numeroPedido;
    private EmpresaResponse empresa;
    private AreaResponse area;
    private ProductoQuimicoResponse productoQuimico;
    private LocalDateTime fechaPedido;
    private String estado;
    private String observaciones;
    private List<ItemPedidoResponse> items;
    private BigDecimal total;
}

