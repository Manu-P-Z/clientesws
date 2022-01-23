package com.manu.Clientes.service;

import com.manu.Clientes.domain.Cliente;

import java.util.Optional;
import java.util.Set;

public interface ClienteService {

    Set<Cliente> findAll();
    Set<Cliente> findByCategory(String category);
    Optional<Cliente> findById(long id);
    Cliente addCliente(Cliente cliente);
    Cliente modifyCliente(long id, Cliente newCliente);
    void deleteCliente(long id);
}
