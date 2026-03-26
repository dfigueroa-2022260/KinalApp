package com.djoserfigueroa.kinalapp.repository;

import com.djoserfigueroa.kinalapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsername(String username);

    List<Usuario> findByRol(String rol);

    List<Usuario> findByEstado(int estado);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
