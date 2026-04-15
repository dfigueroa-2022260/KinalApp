package com.djoserfigueroa.kinalapp.service;

import com.djoserfigueroa.kinalapp.entity.Usuario;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    List<Usuario> listarTodos();

    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorCodigo(int codigoUsuario);

    Optional<Usuario> buscarPorUsername(String username);

    Usuario actualizar(int codigoUsuario, Usuario usuario);

    void eliminar(int codigoUsuario);

    // boolean - Retorna true si existe y false si no existe
    @Transactional(readOnly = true)
    boolean existePorCodigo(int codigoUsuario);

    // Listar usuarios activos (estado = 1)
    List<Usuario> listarActivos();

    // Listar usuarios por rol
    List<Usuario> listarPorRol(String rol);
}