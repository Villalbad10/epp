package com.epp.back.pedidos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    private Long id;
    private String numeroPedido;
    private Empresa empresa;
    private Area area;
    private ProductoQuimico productoQuimico;
    private LocalDateTime fechaPedido;
    private String estado; // PENDIENTE, PROCESADO, ENTREGADO, CANCELADO
    private String observaciones;
    private List<ItemPedido> items;
    private BigDecimal total;
    
    public void agregarItem(ItemPedido item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        calcularTotal();
    }
    
    public void calcularTotal() {
        if (items != null && !items.isEmpty()) {
            this.total = items.stream()
                    .map(ItemPedido::getSubtotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            this.total = BigDecimal.ZERO;
        }
    }
}

