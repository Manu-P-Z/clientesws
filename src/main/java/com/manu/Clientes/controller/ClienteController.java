package com.manu.Clientes.controller;

import com.manu.Clientes.service.ClienteService;
import com.manu.Clientes.domain.Cliente;
import com.manu.Clientes.exception.ClienteNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.manu.Clientes.controller.Response.NOT_FOUND;


@RestController
public class ClienteController {

    private final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/Clientes")
    public ResponseEntity<Set<Cliente>> getClientes(@RequestParam(value = "category", defaultValue = "") String category) {
        logger.info("inicio getClientes");
        Set<Cliente> clientes = null;
        if (category.equals(""))
            clientes = clienteService.findAll();
        else
            clientes = clienteService.findByCategory(category);

        logger.info("fin getClientes");
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/Clientes/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable long id) {
        Cliente cliente = clienteService.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping("/Clientes")
    public ResponseEntity<Cliente> addCliente(@RequestBody Cliente cliente) {
        Cliente addedCliente = clienteService.addCliente(cliente);
        return new ResponseEntity<>(addedCliente, HttpStatus.CREATED);
    }

    @PutMapping("/Clientes/{id}")
    public ResponseEntity<Cliente> modifyCliente(@PathVariable long id, @RequestBody Cliente newCliente) {
        Cliente cliente = clienteService.modifyCliente(id, newCliente);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @DeleteMapping("/Clientes/{id}")
    public ResponseEntity<Response> deleteCliente(@PathVariable long id) {
        clienteService.deleteCliente(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(ClienteNotFoundException pnfe) {
        Response response = Response.errorResonse(NOT_FOUND, pnfe.getMessage());
        logger.error(pnfe.getMessage(), pnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
