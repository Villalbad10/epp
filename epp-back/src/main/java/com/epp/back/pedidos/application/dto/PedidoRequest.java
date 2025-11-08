package com.epp.back.pedidos.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {
    @NotNull(message = "El ID de la empresa es requerido")
    private Long empresaId;
    
    @NotNull(message = "El ID del área es requerido")
    private Long areaId;
    
    @NotNull(message = "El ID del producto químico es requerido")
    private Long productoQuimicoId;
    
    private String observaciones;
    
    @NotEmpty(message = "El pedido debe contener al menos un item")
    @Valid
    private List<ItemPedidoRequest> items;
}

