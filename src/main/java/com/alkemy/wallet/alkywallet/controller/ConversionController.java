package com.alkemy.wallet.alkywallet.controller;

import com.alkemy.wallet.alkywallet.dto.ConversionMonedaRequestDTO;
import com.alkemy.wallet.alkywallet.dto.CuentaDTO;
import com.alkemy.wallet.alkywallet.dto.ResultadoConversionDTO;
import com.alkemy.wallet.alkywallet.service.IConversionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@RestController
@RequestMapping("/api/conversiones")
@RequiredArgsConstructor
public class ConversionController {

    private final IConversionService conversionService;

    @Operation(summary = "Convertir moneda entre cuentas del mismo usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conversión realizada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o fondos insuficientes"),
            @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @PostMapping
    public ResponseEntity<ResultadoConversionDTO> convertirMoneda(
            @Parameter(description = "Datos para la conversión")
            @Valid @RequestBody ConversionMonedaRequestDTO dto) {

        ResultadoConversionDTO resultado = conversionService.convertirMoneda(dto);
        return ResponseEntity.ok(resultado);
    }
}
