package com.manu.Clientes.service;

import com.manu.Clientes.domain.Cliente;
import com.manu.Clientes.exception.ClienteNotFoundException;
import com.manu.Clientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Set<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Set<Cliente> findByCategory(String category) {
        return clienteRepository.findByCategory(category);
    }

    @Override
    public Optional<Cliente> findById(long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Cliente addCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente modifyCliente(long id, Cliente newCliente) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        newCliente.setId(cliente.getId());
        return clienteRepository.save(newCliente);
    }
    @Override
    public void deleteCliente(long id) {
        clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));
        clienteRepository.deleteById(id);
    }
}
