package com.alkemy.wallet.alkywallet.repository;

import com.alkemy.wallet.alkywallet.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.stereotype.Repository;

=======
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
>>>>>>> dev
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
<<<<<<< HEAD
    Optional<Usuario> findByEmail(String email);
=======
    Optional<Usuario> findByIdAndBorradoFalse(Long id);
    Optional<Usuario> findByEmailAndBorradoFalse(String email);
    List<Usuario> findByBorradoFalse();
>>>>>>> dev
}
