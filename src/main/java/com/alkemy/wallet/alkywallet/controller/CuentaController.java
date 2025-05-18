package com.alkemy.wallet.alkywallet.controller;

import com.alkemy.wallet.alkywallet.dto.CuentaDTO;
import com.alkemy.wallet.alkywallet.dto.CuentaRequestDTO;
import com.alkemy.wallet.alkywallet.service.ICuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final ICuentaService cuentaService;

    // Crear una cuenta
    @PostMapping
    public ResponseEntity<CuentaDTO> crearCuenta(@Valid @RequestBody CuentaRequestDTO cuentaRequest) {
        CuentaDTO cuentaCreada = cuentaService.crearCuenta(cuentaRequest);
        return ResponseEntity.status(201).body(cuentaCreada);
    }

    // Obtener una cuenta por ID
    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> obtenerCuenta(@PathVariable Long id) {
        CuentaDTO cuenta = cuentaService.obtenerCuentaPorId(id);
        return ResponseEntity.ok(cuenta);
    }

    // Listar todas las cuentas activas
    @GetMapping
    public ResponseEntity<List<CuentaDTO>> listarCuentas() {
        return ResponseEntity.ok(cuentaService.listarCuentas());
    }

    // Actualizar una cuenta
    @PutMapping("/{id}")
    public ResponseEntity<CuentaDTO> actualizarCuenta(@PathVariable Long id, @Valid @RequestBody CuentaRequestDTO dto) {
        CuentaDTO actualizada = cuentaService.actualizarCuenta(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    // Eliminar (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }

    // Listar cuentas por ID de usuario (por ahora se envía el ID por parámetro)
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CuentaDTO>> listarCuentasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(cuentaService.listarCuentasPorUsuario(usuarioId));
    }

    // Cambiar tipo de cuenta
    @PatchMapping("/{id}/tipo")
    public ResponseEntity<CuentaDTO> cambiarTipoCuenta(@PathVariable Long id, @RequestParam String nuevoTipo) {
        CuentaDTO actualizada = cuentaService.cambiarTipoCuenta(id, nuevoTipo);
        return ResponseEntity.ok(actualizada);
    }
}