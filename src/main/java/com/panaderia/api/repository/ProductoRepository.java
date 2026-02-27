package com.panaderia.api.repository;

import com.panaderia.api.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Map;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // CORRECCIÓN: Ahora contamos cuántos productos hay por categoría 
    // (Ya que no existe la columna stockActual para sumar)
    @Query("SELECT p.categoria as categoria, COUNT(p) as cantidad FROM Producto p GROUP BY p.categoria")
    List<Map<String, Object>> obtenerConteoPorCategoria();
}