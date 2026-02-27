package com.panaderia.api.controller;

import com.panaderia.api.model.Venta;
import com.panaderia.api.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@CrossOrigin(origins = "https://panaderia-frontend-ten.vercel.app")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

    @PostMapping
    public Venta registrarVenta(@RequestBody Venta venta) {
        venta.setFecha(LocalDateTime.now());
        // Importante: Vinculamos los detalles con la venta para que Hibernate sepa guardarlos
        if (venta.getDetalles() != null) {
            venta.getDetalles().forEach(d -> d.setVenta(venta));
        }
        return ventaRepository.save(venta);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Venta> obtenerHistorial(@PathVariable Long clienteId) {
        return ventaRepository.findByClienteIdOrderByFechaDesc(clienteId);
    }
}
