package com.djoserfigueroa.kinalapp.service;

import com.djoserfigueroa.kinalapp.entity.Cliente;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    /*
    * Interfaz: es un contrato que dice QUE metodos debe tener
    * cualquier servicio de Clientes, No tiene
    * Implementacion, solo la definicion de los metodos
    */

    //Metodo que devuelve una lista de todos los Clientes
    List<Cliente> listarTodos();
    /*
    * List<Cliente> lo que hace es devolver una lista
    * de objetos de la entidad Clientes
    */

    //Metodo que guarda en Cliente en base de datos
    Cliente guardar(Cliente cliente);
    //Parametros: recibe un objeto Cliente con los datos a guardar

    //Optional - Contenedor que puede o no tener valor
    //evita el error de NullPointerException
    Optional<Cliente> buscarPorDPI(String dpi);

    //Metodo que actualiza un CLiente
    Cliente  actualizar(String dpi, Cliente cliente);
    /*
    * PArametros - dpi: DPI del cliente a actualizar
    * Cliente cliente: Objeto con los datos nuevos
    * Retorna un objeto de tipo cliente ya actualizado
    **/

    /*
    * Metodo de tipo void para eliminar a un cliente
    * void: no retorna ningun valor o dato
    * Eliminar un cliente por su DPI
    */
    void eliminar(String dpi);


    //bolean - Retorna true si existe y false si no existe
    @Transactional(readOnly = true)
    boolean existePorDPI (String dpi);

    //listar activos
    List<Cliente> listarActivos();

    @Transactional(readOnly = true)
    boolean existePorDpi(String dpi);
}
