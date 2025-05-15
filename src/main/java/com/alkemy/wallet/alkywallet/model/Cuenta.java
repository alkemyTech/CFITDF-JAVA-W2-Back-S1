package com.alkemy.wallet.alkywallet.model;


import jakarta.persistence.*;
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
        @JoinColumn(name = "cuenta_id") // foreign key en Transaccion
        private List<Transaccion> transacciones;

        // Unidireccional hacia Pago
        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
        @JoinColumn(name = "cuenta_id") // foreign key en Pago
        private List<Pago> pagos;

        // Unidireccional hacia Tarjeta
        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
        @JoinColumn(name = "cuenta_id") // foreign key en Tarjeta
        private List<Tarjeta> tarjetas;

       @Column(nullable = false)
       private boolean deleted = false;
}
