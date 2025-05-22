package com.alkemy.wallet.alkywallet.model;

import java.time.LocalDate;
import java.util.ArrayList;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tarjeta")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tarjeta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "numero")
	private String numero;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoTarjeta tipo;

	@Column(name = "fecha_expiracion")
	private LocalDate fechaExpiracion;

	@Column(name = "es_virtual")
	private Boolean esVirtual;

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public enum TipoTarjeta {
		CREDITO, DEBITO, PREPAGA,
	}

	@Column(nullable = false)
	private Boolean deleted = false;

	@ManyToOne
	@JoinColumn(name = "cuenta")
	private Cuenta cuenta;

	@OneToMany(mappedBy = "tarjeta", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Transaccion> transacciones = new ArrayList<>();

}