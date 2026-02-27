package com.panaderia.api.controller;

import com.panaderia.api.model.Venta;
import com.panaderia.api.repository.VentaRepository;
import com.panaderia.api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "http://localhost:5173")
public class ReporteController {

    @Autowired private ProductoRepository productoRepository;
    @Autowired private VentaRepository ventaRepository;

    @GetMapping("/categorias")
    public List<Map<String, Object>> obtenerDatosGrafica() {
        return productoRepository.obtenerConteoPorCategoria();
    }

    // Nueva ruta para la gráfica de barras
    @GetMapping("/ventas-semanales")
    public List<Map<String, Object>> obtenerVentasSemanales() {
        return ventaRepository.obtenerVentasSemanales();
    }

    // Nueva ruta para buscar por calendario
    @GetMapping("/ventas-dia")
    public List<Venta> obtenerVentasPorDia(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ventaRepository.buscarPorFecha(fecha.atStartOfDay(), fecha.atTime(23, 59, 59));
    }

    @GetMapping("/detalle-semanal")
    public List<Venta> obtenerDetalleSemanal() {
        // Calculamos el lunes y domingo de la semana actual
        LocalDate ahora = LocalDate.now();
        LocalDateTime inicio = ahora.with(java.time.DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime fin = ahora.with(java.time.DayOfWeek.SUNDAY).atTime(23, 59, 59);
        
        return ventaRepository.findAllByFechaBetweenOrderByFechaAsc(inicio, fin);
    }
}
