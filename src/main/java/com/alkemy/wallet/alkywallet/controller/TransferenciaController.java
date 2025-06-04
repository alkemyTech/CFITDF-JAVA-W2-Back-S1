package com.alkemy.wallet.alkywallet.controller;

import com.alkemy.wallet.alkywallet.dto.TransferenciaRequestDTO;
import com.alkemy.wallet.alkywallet.service.ITransferenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Transferencias", description = "Operaciones de transferencia entre cuentas")
@RestController
@RequestMapping("/api/transferencias")
@RequiredArgsConstructor
public class TransferenciaController {

    private final ITransferenciaService transferenciaService;

    @Operation(summary = "Realizar transferencia entre cuentas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transferencia realizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o error de negocio")
    })
    @PostMapping
    public ResponseEntity<String> transferir(@Valid @RequestBody TransferenciaRequestDTO dto) {
        transferenciaService.realizarTransferencia(dto);
        return ResponseEntity.ok("Transferencia realizada con éxito");
    }
}