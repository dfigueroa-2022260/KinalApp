package com.djoserfigueroa.kinalapp.repository;

import com.djoserfigueroa.kinalapp.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,String> {
    List<Cliente> findByEstado(int estado);
}
