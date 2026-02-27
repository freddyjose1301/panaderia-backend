package com.panaderia.api.controller;

import com.panaderia.api.model.Cliente;
import com.panaderia.api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "https://panaderia-frontend-ten.vercel.app")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Importante añadir este GetMapping para traer la lista
    @GetMapping
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    @PostMapping
    public Cliente guardarCliente(@RequestBody Cliente cliente) {
        // Si el cliente ya existe por cédula, lo devolvemos; si no, lo guardamos
        Cliente existente = clienteRepository.findByCedula(cliente.getCedula());
        return (existente != null) ? existente : clienteRepository.save(cliente);
    }

    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<Cliente> buscarPorCedula(@PathVariable String cedula) {
        Cliente cliente = clienteRepository.findByCedula(cedula);
        if (cliente != null) {
            return ResponseEntity.ok(cliente); // Cliente encontrado (Status 200)
        }
        return ResponseEntity.notFound().build(); // Cliente NO encontrado (Status 404)
    }
}
