package com.alkemy.wallet.alkywallet.controller;

import com.alkemy.wallet.alkywallet.dto.TransaccionRequestDTO;
import com.alkemy.wallet.alkywallet.dto.TransaccionResponseDTO;
import com.alkemy.wallet.alkywallet.service.ITransaccionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    private final ITransaccionService transaccionService;

    public TransaccionController(ITransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping
    public ResponseEntity<TransaccionResponseDTO> crearTransaccion(@Valid @RequestBody TransaccionRequestDTO requestDTO) {
        TransaccionResponseDTO responseDTO = transaccionService.crearTransaccion(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransaccionResponseDTO>> listarTodas() {
        List<TransaccionResponseDTO> dtos = transaccionService.obtenerTodas();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return transaccionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransaccionResponseDTO> actualizarTransaccion(@PathVariable Long id,
                                                                        @Valid @RequestBody TransaccionRequestDTO requestDTO) {
        try {
            TransaccionResponseDTO responseDTO = transaccionService.actualizarTransaccion(id, requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTransaccion(@PathVariable Long id) {
        try {
            transaccionService.eliminarTransaccion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
