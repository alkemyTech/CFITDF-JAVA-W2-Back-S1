package com.alkemy.wallet.alkywallet.controller;

import com.alkemy.wallet.alkywallet.dto.CuentaDTO;
import com.alkemy.wallet.alkywallet.dto.CuentaRequestDTO;
import com.alkemy.wallet.alkywallet.service.ICuentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cuentas", description = "Operaciones sobre cuentas bancarias")
@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final ICuentaService cuentaService;

    @Operation(summary = "Crear una nueva cuenta")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cuenta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<CuentaDTO> crearCuenta(@Valid @RequestBody CuentaRequestDTO cuentaRequest) {
        CuentaDTO cuentaCreada = cuentaService.crearCuenta(cuentaRequest);
        return ResponseEntity.status(201).body(cuentaCreada);
    }

    @Operation(summary = "Obtener una cuenta por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> obtenerCuenta(
            @Parameter(description = "ID de la cuenta") @PathVariable Long id) {
        CuentaDTO cuenta = cuentaService.obtenerCuentaPorId(id);
        return ResponseEntity.ok(cuenta);
    }

    @Operation(summary = "Listar todas las cuentas activas")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<CuentaDTO>> listarCuentas() {
        return ResponseEntity.ok(cuentaService.listarCuentas());
    }

    @Operation(summary = "Actualizar una cuenta existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuenta actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CuentaDTO> actualizarCuenta(
            @Parameter(description = "ID de la cuenta") @PathVariable Long id,
            @Valid @RequestBody CuentaRequestDTO dto) {
        CuentaDTO actualizada = cuentaService.actualizarCuenta(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @Operation(summary = "Eliminar (soft delete) una cuenta")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cuenta eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(
            @Parameter(description = "ID de la cuenta") @PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar cuentas por ID de usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuentas del usuario listadas correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CuentaDTO>> listarCuentasPorUsuario(
            @Parameter(description = "ID del usuario") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(cuentaService.listarCuentasPorUsuario(usuarioId));
    }

    @Operation(summary = "Cambiar el tipo de cuenta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de cuenta actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Tipo inválido"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @PatchMapping("/{id}/tipo")
    public ResponseEntity<CuentaDTO> cambiarTipoCuenta(
            @Parameter(description = "ID de la cuenta") @PathVariable Long id,
            @Parameter(description = "Nuevo tipo de cuenta (ej: CAJA_AHORRO)") @RequestParam String nuevoTipo) {
        CuentaDTO actualizada = cuentaService.cambiarTipoCuenta(id, nuevoTipo);
        return ResponseEntity.ok(actualizada);
    }
}
