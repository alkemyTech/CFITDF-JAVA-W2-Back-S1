package com.alkemy.wallet.alkywallet.repository;

import com.alkemy.wallet.alkywallet.model.Cuenta;
import com.alkemy.wallet.alkywallet.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICuentaRepository extends JpaRepository<Cuenta, Long> {

    // Buscar todas las cuentas de un usuario específico
    List<Cuenta> findByUsuario(Usuario usuario);

    // Buscar por tipo de cuenta y usuario 
    Cuenta findByUsuarioAndTipo(Usuario usuario, Enum tipo);

    // Buscar todas las cuentas que no están marcadas como eliminadas
    List<Cuenta> findByDeletedFalse();

    // Buscar una cuenta por ID y que no esté eliminada
    Cuenta findByIdAndDeletedFalse(Long id);
}
