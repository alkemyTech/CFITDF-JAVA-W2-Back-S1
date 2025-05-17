package com.alkemy.wallet.alkywallet.repository;

import com.alkemy.wallet.alkywallet.model.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {

}
