package com.panaderia.api.repository;

import com.panaderia.api.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Aquí podrías buscar clientes por cédula más adelante
    Cliente findByCedula(String cedula);
}