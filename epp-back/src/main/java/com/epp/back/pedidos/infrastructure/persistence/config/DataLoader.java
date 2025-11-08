package com.epp.back.pedidos.infrastructure.persistence.config;

import com.epp.back.pedidos.infrastructure.persistence.entity.*;
import com.epp.back.pedidos.infrastructure.persistence.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    
    private final EmpresaJpaRepository empresaRepository;
    private final AreaJpaRepository areaRepository;
    private final ProductoQuimicoJpaRepository productoQuimicoRepository;
    private final EPPJpaRepository eppRepository;
    private final PedidoJpaRepository pedidoRepository;
    
    @Override
    @Transactional
    public void run(String... args) {
        if (empresaRepository.count() == 0) {
            log.info("Cargando datos de prueba...");
            cargarDatosIniciales();
            log.info("Datos de prueba cargados exitosamente");
        } else {
            log.info("Los datos ya existen, omitiendo carga inicial");
        }
    }
    
    private void cargarDatosIniciales() {
        // Crear empresas
        EmpresaEntity empresa1 = EmpresaEntity.builder()
                .nombre("Química Industrial S.A.")
                .ruc("20123456789")
                .direccion("Av. Industrial 123")
                .telefono("987654321")
                .email("contacto@quimicaindustrial.com")
                .build();
        empresa1 = empresaRepository.save(empresa1);
        
        EmpresaEntity empresa2 = EmpresaEntity.builder()
                .nombre("Productos Químicos del Norte")
                .ruc("20987654321")
                .direccion("Av. Norte 456")
                .telefono("987654322")
                .email("info@quimicosnorte.com")
                .build();
        empresa2 = empresaRepository.save(empresa2);
        
        // Crear áreas
        AreaEntity area1 = AreaEntity.builder()
                .nombre("Producción")
                .descripcion("Área de producción química")
                .empresa(empresa1)
                .build();
        area1 = areaRepository.save(area1);
        
        AreaEntity area2 = AreaEntity.builder()
                .nombre("Laboratorio")
                .descripcion("Área de análisis y pruebas")
                .empresa(empresa1)
                .build();
        area2 = areaRepository.save(area2);
        
        AreaEntity area3 = AreaEntity.builder()
                .nombre("Almacén")
                .descripcion("Área de almacenamiento")
                .empresa(empresa1)
                .build();
        area3 = areaRepository.save(area3);
        
        AreaEntity area4 = AreaEntity.builder()
                .nombre("Producción")
                .descripcion("Área de producción")
                .empresa(empresa2)
                .build();
        area4 = areaRepository.save(area4);
        
        // Crear productos químicos
        ProductoQuimicoEntity producto1 = ProductoQuimicoEntity.builder()
                .nombre("Ácido Sulfúrico")
                .codigo("PQ-001")
                .descripcion("Ácido sulfúrico concentrado")
                .empresa(empresa1)
                .build();
        producto1 = productoQuimicoRepository.save(producto1);
        
        ProductoQuimicoEntity producto2 = ProductoQuimicoEntity.builder()
                .nombre("Hidróxido de Sodio")
                .codigo("PQ-002")
                .descripcion("Soda cáustica")
                .empresa(empresa1)
                .build();
        producto2 = productoQuimicoRepository.save(producto2);
        
        ProductoQuimicoEntity producto3 = ProductoQuimicoEntity.builder()
                .nombre("Ácido Clorhídrico")
                .codigo("PQ-003")
                .descripcion("Ácido clorhídrico concentrado")
                .empresa(empresa2)
                .build();
        producto3 = productoQuimicoRepository.save(producto3);
        
        // Crear EPPs
        EPPEntity epp1 = EPPEntity.builder()
                .codigo("EPP-001")
                .nombre("Guantes de Nitrilo")
                .descripcion("Guantes resistentes a químicos")
                .tipo("guantes")
                .precioUnitario(new BigDecimal("25.50"))
                .stockDisponible(500)
                .activo(true)
                .build();
        epp1 = eppRepository.save(epp1);
        
        EPPEntity epp2 = EPPEntity.builder()
                .codigo("EPP-002")
                .nombre("Respirador N95")
                .descripcion("Mascarilla respiratoria N95")
                .tipo("respiradores")
                .precioUnitario(new BigDecimal("15.00"))
                .stockDisponible(300)
                .activo(true)
                .build();
        epp2 = eppRepository.save(epp2);
        
        EPPEntity epp3 = EPPEntity.builder()
                .codigo("EPP-003")
                .nombre("Gafas de Seguridad")
                .descripcion("Gafas protectoras anti-vaho")
                .tipo("gafas")
                .precioUnitario(new BigDecimal("35.00"))
                .stockDisponible(200)
                .activo(true)
                .build();
        epp3 = eppRepository.save(epp3);
        
        EPPEntity epp4 = EPPEntity.builder()
                .codigo("EPP-004")
                .nombre("Bata de Laboratorio")
                .descripcion("Bata resistente a químicos")
                .tipo("batas")
                .precioUnitario(new BigDecimal("85.00"))
                .stockDisponible(150)
                .activo(true)
                .build();
        epp4 = eppRepository.save(epp4);
        
        EPPEntity epp5 = EPPEntity.builder()
                .codigo("EPP-005")
                .nombre("Zapatos de Seguridad")
                .descripcion("Zapatos con puntera de acero")
                .tipo("zapatos")
                .precioUnitario(new BigDecimal("120.00"))
                .stockDisponible(100)
                .activo(true)
                .build();
        epp5 = eppRepository.save(epp5);
        
        // Crear pedidos
        crearPedidos(empresa1, area1, area2, area3, producto1, producto2, epp1, epp2, epp3, epp4, epp5);
    }
    
    private void crearPedidos(EmpresaEntity empresa1, AreaEntity area1, AreaEntity area2, AreaEntity area3,
                              ProductoQuimicoEntity producto1, ProductoQuimicoEntity producto2,
                              EPPEntity epp1, EPPEntity epp2, EPPEntity epp3, EPPEntity epp4, EPPEntity epp5) {
        
        // Pedido 1
        PedidoEntity pedido1 = PedidoEntity.builder()
                .numeroPedido("PED-0000001")
                .empresa(empresa1)
                .area(area1)
                .productoQuimico(producto1)
                .fechaPedido(LocalDateTime.now().minusDays(10))
                .estado("ENTREGADO")
                .observaciones("Pedido urgente")
                .total(new BigDecimal("102.00"))
                .build();
        
        ItemPedidoEntity item1_1 = ItemPedidoEntity.builder()
                .pedido(pedido1)
                .epp(epp1)
                .cantidad(2)
                .precioUnitario(epp1.getPrecioUnitario())
                .subtotal(new BigDecimal("51.00"))
                .build();
        
        ItemPedidoEntity item1_2 = ItemPedidoEntity.builder()
                .pedido(pedido1)
                .epp(epp2)
                .cantidad(2)
                .precioUnitario(epp2.getPrecioUnitario())
                .subtotal(new BigDecimal("30.00"))
                .build();
        
        ItemPedidoEntity item1_3 = ItemPedidoEntity.builder()
                .pedido(pedido1)
                .epp(epp3)
                .cantidad(1)
                .precioUnitario(epp3.getPrecioUnitario())
                .subtotal(new BigDecimal("35.00"))
                .build();
        
        pedido1.setItems(Arrays.asList(item1_1, item1_2, item1_3));
        pedidoRepository.save(pedido1);
        
        // Pedido 2
        PedidoEntity pedido2 = PedidoEntity.builder()
                .numeroPedido("PED-0000002")
                .empresa(empresa1)
                .area(area2)
                .productoQuimico(producto2)
                .fechaPedido(LocalDateTime.now().minusDays(8))
                .estado("PROCESADO")
                .observaciones("Para laboratorio")
                .total(new BigDecimal("170.00"))
                .build();
        
        ItemPedidoEntity item2_1 = ItemPedidoEntity.builder()
                .pedido(pedido2)
                .epp(epp4)
                .cantidad(2)
                .precioUnitario(epp4.getPrecioUnitario())
                .subtotal(new BigDecimal("170.00"))
                .build();
        
        pedido2.setItems(Arrays.asList(item2_1));
        pedidoRepository.save(pedido2);
        
        // Pedido 3
        PedidoEntity pedido3 = PedidoEntity.builder()
                .numeroPedido("PED-0000003")
                .empresa(empresa1)
                .area(area1)
                .productoQuimico(producto1)
                .fechaPedido(LocalDateTime.now().minusDays(5))
                .estado("PENDIENTE")
                .observaciones(null)
                .total(new BigDecimal("75.00"))
                .build();
        
        ItemPedidoEntity item3_1 = ItemPedidoEntity.builder()
                .pedido(pedido3)
                .epp(epp2)
                .cantidad(5)
                .precioUnitario(epp2.getPrecioUnitario())
                .subtotal(new BigDecimal("75.00"))
                .build();
        
        pedido3.setItems(Arrays.asList(item3_1));
        pedidoRepository.save(pedido3);
        
        // Pedido 4
        PedidoEntity pedido4 = PedidoEntity.builder()
                .numeroPedido("PED-0000004")
                .empresa(empresa1)
                .area(area3)
                .productoQuimico(producto2)
                .fechaPedido(LocalDateTime.now().minusDays(3))
                .estado("PENDIENTE")
                .observaciones("Reposición de stock")
                .total(new BigDecimal("255.00"))
                .build();
        
        ItemPedidoEntity item4_1 = ItemPedidoEntity.builder()
                .pedido(pedido4)
                .epp(epp1)
                .cantidad(10)
                .precioUnitario(epp1.getPrecioUnitario())
                .subtotal(new BigDecimal("255.00"))
                .build();
        
        pedido4.setItems(Arrays.asList(item4_1));
        pedidoRepository.save(pedido4);
        
        // Pedido 5
        PedidoEntity pedido5 = PedidoEntity.builder()
                .numeroPedido("PED-0000005")
                .empresa(empresa1)
                .area(area2)
                .productoQuimico(producto1)
                .fechaPedido(LocalDateTime.now().minusDays(2))
                .estado("ENTREGADO")
                .observaciones(null)
                .total(new BigDecimal("435.00"))
                .build();
        
        ItemPedidoEntity item5_1 = ItemPedidoEntity.builder()
                .pedido(pedido5)
                .epp(epp3)
                .cantidad(5)
                .precioUnitario(epp3.getPrecioUnitario())
                .subtotal(new BigDecimal("175.00"))
                .build();
        
        ItemPedidoEntity item5_2 = ItemPedidoEntity.builder()
                .pedido(pedido5)
                .epp(epp4)
                .cantidad(2)
                .precioUnitario(epp4.getPrecioUnitario())
                .subtotal(new BigDecimal("170.00"))
                .build();
        
        ItemPedidoEntity item5_3 = ItemPedidoEntity.builder()
                .pedido(pedido5)
                .epp(epp5)
                .cantidad(1)
                .precioUnitario(epp5.getPrecioUnitario())
                .subtotal(new BigDecimal("120.00"))
                .build();
        
        pedido5.setItems(Arrays.asList(item5_1, item5_2, item5_3));
        pedidoRepository.save(pedido5);
        
        // Pedido 6
        PedidoEntity pedido6 = PedidoEntity.builder()
                .numeroPedido("PED-0000006")
                .empresa(empresa1)
                .area(area1)
                .productoQuimico(producto2)
                .fechaPedido(LocalDateTime.now().minusDays(1))
                .estado("PENDIENTE")
                .observaciones("Equipo nuevo")
                .total(new BigDecimal("120.00"))
                .build();
        
        ItemPedidoEntity item6_1 = ItemPedidoEntity.builder()
                .pedido(pedido6)
                .epp(epp5)
                .cantidad(1)
                .precioUnitario(epp5.getPrecioUnitario())
                .subtotal(new BigDecimal("120.00"))
                .build();
        
        pedido6.setItems(Arrays.asList(item6_1));
        pedidoRepository.save(pedido6);
        
        // Pedido 7
        PedidoEntity pedido7 = PedidoEntity.builder()
                .numeroPedido("PED-0000007")
                .empresa(empresa1)
                .area(area3)
                .productoQuimico(producto1)
                .fechaPedido(LocalDateTime.now().minusHours(12))
                .estado("PENDIENTE")
                .observaciones(null)
                .total(new BigDecimal("51.00"))
                .build();
        
        ItemPedidoEntity item7_1 = ItemPedidoEntity.builder()
                .pedido(pedido7)
                .epp(epp1)
                .cantidad(2)
                .precioUnitario(epp1.getPrecioUnitario())
                .subtotal(new BigDecimal("51.00"))
                .build();
        
        pedido7.setItems(Arrays.asList(item7_1));
        pedidoRepository.save(pedido7);
        
        // Pedido 8
        PedidoEntity pedido8 = PedidoEntity.builder()
                .numeroPedido("PED-0000008")
                .empresa(empresa1)
                .area(area2)
                .productoQuimico(producto2)
                .fechaPedido(LocalDateTime.now().minusHours(6))
                .estado("PROCESADO")
                .observaciones("Pedido estándar")
                .total(new BigDecimal("140.00"))
                .build();
        
        ItemPedidoEntity item8_1 = ItemPedidoEntity.builder()
                .pedido(pedido8)
                .epp(epp2)
                .cantidad(5)
                .precioUnitario(epp2.getPrecioUnitario())
                .subtotal(new BigDecimal("75.00"))
                .build();
        
        ItemPedidoEntity item8_2 = ItemPedidoEntity.builder()
                .pedido(pedido8)
                .epp(epp3)
                .cantidad(2)
                .precioUnitario(epp3.getPrecioUnitario())
                .subtotal(new BigDecimal("70.00"))
                .build();
        
        pedido8.setItems(Arrays.asList(item8_1, item8_2));
        pedidoRepository.save(pedido8);
        
        // Pedido 9
        PedidoEntity pedido9 = PedidoEntity.builder()
                .numeroPedido("PED-0000009")
                .empresa(empresa1)
                .area(area1)
                .productoQuimico(producto1)
                .fechaPedido(LocalDateTime.now().minusHours(3))
                .estado("PENDIENTE")
                .observaciones("Urgente")
                .total(new BigDecimal("85.00"))
                .build();
        
        ItemPedidoEntity item9_1 = ItemPedidoEntity.builder()
                .pedido(pedido9)
                .epp(epp4)
                .cantidad(1)
                .precioUnitario(epp4.getPrecioUnitario())
                .subtotal(new BigDecimal("85.00"))
                .build();
        
        pedido9.setItems(Arrays.asList(item9_1));
        pedidoRepository.save(pedido9);
        
        // Pedido 10
        PedidoEntity pedido10 = PedidoEntity.builder()
                .numeroPedido("PED-0000010")
                .empresa(empresa1)
                .area(area3)
                .productoQuimico(producto2)
                .fechaPedido(LocalDateTime.now().minusHours(1))
                .estado("PENDIENTE")
                .observaciones("Pedido de prueba")
                .total(new BigDecimal("60.00"))
                .build();
        
        ItemPedidoEntity item10_1 = ItemPedidoEntity.builder()
                .pedido(pedido10)
                .epp(epp3)
                .cantidad(1)
                .precioUnitario(epp3.getPrecioUnitario())
                .subtotal(new BigDecimal("35.00"))
                .build();
        
        ItemPedidoEntity item10_2 = ItemPedidoEntity.builder()
                .pedido(pedido10)
                .epp(epp2)
                .cantidad(2)
                .precioUnitario(epp2.getPrecioUnitario())
                .subtotal(new BigDecimal("30.00"))
                .build();
        
        pedido10.setItems(Arrays.asList(item10_1, item10_2));
        pedidoRepository.save(pedido10);
    }
}

