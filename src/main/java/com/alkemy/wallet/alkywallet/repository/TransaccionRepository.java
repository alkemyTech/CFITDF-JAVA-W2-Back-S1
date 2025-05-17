package com.alkemy.wallet.alkywallet.repository;

import com.alkemy.wallet.alkywallet.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository <Transaccion, Long>{
}
