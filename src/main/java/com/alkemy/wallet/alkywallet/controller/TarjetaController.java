package com.alkemy.wallet.alkywallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.wallet.alkywallet.dto.TarjetaDTO;
import com.alkemy.wallet.alkywallet.service.TarjetaServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Tarjetas", description = "Operaciones sobre Tarjetas")
@RequestMapping("/api")
public class TarjetaController {
	@Autowired
	private TarjetaServiceImpl tarjetaService;

	// Crear
	@PostMapping("/tarjetas")
	@Operation(summary = "Crear una nueva tarjeta")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Tarjeta creada correctamente"),
			@ApiResponse(responseCode = "400", description = "Datos inválidos") })
	public ResponseEntity<TarjetaDTO> crearCuenta(@RequestBody TarjetaDTO tarjetaPorCrear) {
		TarjetaDTO dto = tarjetaService.crearTarjeta(tarjetaPorCrear);
		return ResponseEntity.status(201).body(tarjetaPorCrear);
	}

	// Listar
	@GetMapping("/tarjetas")
	@Operation(summary = "Obtener todos las tarjetas")
	@ApiResponse(responseCode = "201", description = "Lista de tarjetas obtenida correctamente")
	public ResponseEntity<List<TarjetaDTO>> listarTarjetas() {
		return ResponseEntity.ok(tarjetaService.listarTarjetas());
	}

	@GetMapping("/tarjetas/usuario/{id_usuario}")
	@Operation(summary = "Obtener todos las tarjetas de un usuario por ID")
	@ApiResponse(responseCode = "201", description = "Lista de tarjetas obtenida correctamente")
	public ResponseEntity<List<TarjetaDTO>> listarTarjetasDeUsuario(@PathVariable Long id_usuario) {
		return ResponseEntity.ok(tarjetaService.listarTarjetasPorUsuario(id_usuario));
	}

	// Actualizar
	@PutMapping("/tarjetas/{id}")
	@Operation(summary = "Editar una tarjeta")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Tarjeta actualizada correctamente"),
			@ApiResponse(responseCode = "400", description = "Datos inválidos"),
			@ApiResponse(responseCode = "404", description = "Tarjeta no encontrada") })
	public ResponseEntity<TarjetaDTO> actualizarTarjeta(@PathVariable Long id, @RequestBody TarjetaDTO dto) {
		TarjetaDTO tarjetaPorActualizar = tarjetaService.editarTarjeta(id, dto);
		return ResponseEntity.ok(tarjetaPorActualizar);
	}

	@DeleteMapping("/tarjetas/{id}")
	@Operation(summary = "Eliminar una tarjeta")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Tarjeta eliminada correctamente"),
			@ApiResponse(responseCode = "404", description = "Tarjeta no encontrada") })
	public ResponseEntity<Void> eliminarTarjeta(@PathVariable Long id) {
		tarjetaService.eliminarTarjeta(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Listar una tarjeta por su ID")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Tarjeta listada correctamente"),
			@ApiResponse(responseCode = "404", description = "Tarjeta no encontrada") })
	@GetMapping("/tarjetas/{id}")
	public ResponseEntity<TarjetaDTO> listarTarjetaPorId(@PathVariable Long id) {
		return ResponseEntity.ok(tarjetaService.listarTarjetaPorId(id));
	}
}
