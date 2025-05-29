package com.alkemy.wallet.alkywallet.controller;

import com.alkemy.wallet.alkywallet.dto.PagoRequestDTO;
import com.alkemy.wallet.alkywallet.service.IPagoService;
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

@Tag(name = "Pagos", description = "Operaciones sobre pagos")
@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final IPagoService pagoService;

    @Operation(summary = "Crear un nuevo pago")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pago creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<PagoRequestDTO> crearPago(
            @Valid @RequestBody PagoRequestDTO pagoRequest) {
        PagoRequestDTO creado = pagoService.createPago(pagoRequest);
        return ResponseEntity.status(201).body(creado);
    }

    @Operation(summary = "Obtener todos los pagos")
    @ApiResponse(responseCode = "200", description = "Listado de pagos obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<PagoRequestDTO>> listarPagos() {
        return ResponseEntity.ok(pagoService.getAllPagos());
    }

    @Operation(summary = "Obtener un pago por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoRequestDTO> obtenerPago(
            @Parameter(description = "ID del pago") @PathVariable Long id) {
        PagoRequestDTO pago = pagoService.getPagoById(id);
        return ResponseEntity.ok(pago);
    }

    @Operation(summary = "Actualizar un pago existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PagoRequestDTO> actualizarPago(
            @Parameter(description = "ID del pago") @PathVariable Long id,
            @Valid @RequestBody PagoRequestDTO pagoRequest) {
        PagoRequestDTO actualizado = pagoService.updatePago(id, pagoRequest);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Eliminar un pago")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pago eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPago(
            @Parameter(description = "ID del pago") @PathVariable Long id) {
        pagoService.deletePago(id);
        return ResponseEntity.noContent().build();
    }
}
