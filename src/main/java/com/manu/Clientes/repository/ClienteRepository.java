package com.manu.Clientes.repository;

import com.manu.Clientes.domain.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    Set<Cliente> findAll();
    Cliente findByNombre(String name);
    Set<Cliente> findByCategory(String category);
}
