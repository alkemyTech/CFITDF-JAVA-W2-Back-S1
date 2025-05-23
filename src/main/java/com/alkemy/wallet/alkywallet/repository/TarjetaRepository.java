package com.alkemy.wallet.alkywallet.repository;

import com.alkemy.wallet.alkywallet.model.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {
    public Optional<Tarjeta> findByIdAndDeletedFalse(Long id);
    public List<Tarjeta> findByDeletedFalse();

}
