package com.alkemy.wallet.alkywallet.repository;

import com.alkemy.wallet.alkywallet.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPagoRepository extends JpaRepository<Pago, Long> {

    // Buscar todos los pagos de una cuenta específica
    List<Pago> findByCuentaId(Long cuentaId);

    // Opcional: buscar por comercio (útil para filtros en el frontend)
    List<Pago> findByComercioContainingIgnoreCase(String comercio);
}