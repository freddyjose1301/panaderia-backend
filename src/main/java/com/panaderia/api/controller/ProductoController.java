package com.panaderia.api.controller;

import com.panaderia.api.model.Producto;
import com.panaderia.api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "https://panaderia-frontend-ten.vercel.app")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // --- 1. MÉTODO PARA EDITAR (PUT) ---
    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto productoDetalles) {
        return productoRepository.findById(id)
            .map(producto -> {
                producto.setNombre(productoDetalles.getNombre());
                producto.setCategoria(productoDetalles.getCategoria());
                producto.setPrecio(productoDetalles.getPrecio());
                return productoRepository.save(producto);
            })
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con id " + id));
    }

    // --- 2. MÉTODO PARA ELIMINAR (DELETE) ---
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoRepository.deleteById(id);
    }

    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @PostMapping
    public Producto guardarProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }
}