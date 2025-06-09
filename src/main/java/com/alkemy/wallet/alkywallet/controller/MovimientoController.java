package com.alkemy.wallet.alkywallet.controller;

import com.alkemy.wallet.alkywallet.dto.MovimientoDTO;
import com.alkemy.wallet.alkywallet.service.IMovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
@Tag(name = "Movimientos", description = "Ver movimientos de una cuenta")
public class MovimientoController {

    private final IMovimientoService movimientoService;

    @Operation(summary = "Obtener movimientos de una cuenta")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de movimientos"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @GetMapping("/{cuentaId}")
    public ResponseEntity<List<MovimientoDTO>> obtenerMovimientos(
            @Parameter(description = "ID de la cuenta") @PathVariable Long cuentaId) {
        List<MovimientoDTO> movimientos = movimientoService.obtenerMovimientosPorCuenta(cuentaId);
        return ResponseEntity.ok(movimientos);
    }
}
