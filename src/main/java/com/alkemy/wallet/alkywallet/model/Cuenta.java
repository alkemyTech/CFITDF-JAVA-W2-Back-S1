package com.alkemy.wallet.alkywallet.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cuentas")
public class Cuenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@PositiveOrZero(message = "El saldo no puede ser negativo")
	private Double saldo;

	@NotNull(message = "El tipo de cuenta es obligatorio")
	@Enumerated(EnumType.STRING)
	private TipoCuenta tipo;

	// Unidireccional hacia Usuario
	@NotNull(message = "La cuenta debe estar asociada a un usuario")
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	// Unidireccional hacia Transaccion
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cuenta_id")
	private List<Transaccion> transacciones;

	// Unidireccional hacia Pago
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cuenta_id")
	private List<Pago> pagos;

	// Unidireccional hacia Tarjeta
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cuenta_id")
	private List<Tarjeta> tarjetas;

	@Column(nullable = false)
	private boolean deleted = false;

}