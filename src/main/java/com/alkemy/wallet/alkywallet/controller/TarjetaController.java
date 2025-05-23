package com.alkemy.wallet.alkywallet.controller;

import com.alkemy.wallet.alkywallet.dto.CuentaDTO;
import com.alkemy.wallet.alkywallet.dto.TarjetaDTO;
import com.alkemy.wallet.alkywallet.service.ITarjetaService;
import com.alkemy.wallet.alkywallet.service.TarjetaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TarjetaController {
    @Autowired
    private TarjetaServiceImpl tarjetaService;

    // Crear
    @PostMapping("/tarjetas")
    public ResponseEntity<TarjetaDTO> crearCuenta(@RequestBody TarjetaDTO tarjetaPorCrear) {
        TarjetaDTO dto = tarjetaService.crearTarjeta(tarjetaPorCrear);
        return ResponseEntity.status(201).body(tarjetaPorCrear);
    }

    // Listar
    @GetMapping("/tarjetas")
    public ResponseEntity<List<TarjetaDTO>> listarTarjetas() {
        return ResponseEntity.ok(tarjetaService.listarTarjetas());
    }

    // Actualizar
    @PutMapping("/tarjetas/{id}")
    public ResponseEntity<TarjetaDTO> actualizarTarjeta(@PathVariable Long id, @RequestBody TarjetaDTO dto) {
        TarjetaDTO tarjetaPorActualizar = tarjetaService.editarTarjeta(id, dto);
        return ResponseEntity.ok(tarjetaPorActualizar);
    }

    @DeleteMapping("/tarjetas/{id}")
    public ResponseEntity<Void> eliminarTarjeta(@PathVariable Long id) {
        tarjetaService.eliminarTarjeta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tarjetas/{id}")
    public ResponseEntity<TarjetaDTO> listarTarjetaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tarjetaService.listarTarjetaPorId(id));
    }
}

