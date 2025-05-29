package com.alkemy.wallet.alkywallet.repository;

import com.alkemy.wallet.alkywallet.model.TipoTransaccion;
import com.alkemy.wallet.alkywallet.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    // Listar transacciones por tipo
    List<Transaccion> findByTipoTransaccion(TipoTransaccion tipoTransaccion);

    // Listar transacciones entre fechas
    List<Transaccion> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    // Listar transacciones por tarjeta
    List<Transaccion> findByTarjetaId(Long tarjetaId);
}
