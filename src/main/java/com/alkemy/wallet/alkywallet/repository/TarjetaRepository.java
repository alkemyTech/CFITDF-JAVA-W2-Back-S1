package com.alkemy.wallet.alkywallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.wallet.alkywallet.model.Tarjeta;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {
	public Optional<Tarjeta> findByIdAndDeletedFalse(Long id);

	public List<Tarjeta> findByDeletedFalse();

	public List<Tarjeta> findByUsuarioIdAndDeletedFalse(Long id);
}
