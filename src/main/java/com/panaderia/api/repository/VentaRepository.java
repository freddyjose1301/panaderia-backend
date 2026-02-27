package com.panaderia.api.repository;

import com.panaderia.api.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByClienteIdOrderByFechaDesc(Long clienteId);

    // Consulta para la gráfica de barras de los últimos 7 días
    @Query(value = "SELECT CAST(fecha AS DATE) as fecha, SUM(total) as total " +
                   "FROM ventas WHERE fecha >= CURRENT_DATE - INTERVAL '7 days' " +
                   "GROUP BY CAST(fecha AS DATE) ORDER BY fecha ASC", nativeQuery = true)
    List<Map<String, Object>> obtenerVentasSemanales();

    // Consulta para buscar ventas de un día específico (Calendario)
    @Query("SELECT v FROM Venta v WHERE v.fecha >= :inicio AND v.fecha <= :fin")
    List<Venta> buscarPorFecha(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
    List<Venta> findAllByFechaBetweenOrderByFechaAsc(LocalDateTime inicio, LocalDateTime fin);
}
