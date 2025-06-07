package com.alkemy.wallet.alkywallet.repository;

import com.alkemy.wallet.alkywallet.model.Cuenta;
import com.alkemy.wallet.alkywallet.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    Optional<Cuenta> findByCbu(String cbu);

    boolean existsByCbu(String cbu);

    @Query("SELECT c FROM Cuenta c " +
            "LEFT JOIN FETCH c.pagos " +
            "LEFT JOIN FETCH c.transacciones " +
            "WHERE c.id = :id AND c.deleted = false")
    Cuenta findByIdWithMovimientos(@Param("id") Long id);




}
