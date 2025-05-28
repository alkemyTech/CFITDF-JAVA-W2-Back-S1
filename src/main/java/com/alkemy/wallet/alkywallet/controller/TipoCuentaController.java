package com.alkemy.wallet.alkywallet.controller;

import com.alkemy.wallet.alkywallet.dto.TipoCuentaDTO;
import com.alkemy.wallet.alkywallet.service.ItipoCuentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-cuenta")
@RequiredArgsConstructor
public class TipoCuentaController {

    private final ItipoCuentaService tipoCuentaService;

    @Operation(summary = "Obtener todos los tipos de cuenta disponibles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tipos de cuenta obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<TipoCuentaDTO>> listarTiposDeCuenta() {
        List<TipoCuentaDTO> tipos = tipoCuentaService.obtenerTiposDeCuenta();
        return ResponseEntity.ok(tipos);
    }
}