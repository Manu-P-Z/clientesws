package com.manu.Clientes.controller;

import com.manu.Clientes.domain.Cliente;
import com.manu.Clientes.exception.ClienteNotFoundException;
import com.manu.Clientes.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.manu.Clientes.controller.Response.NOT_FOUND;


@RestController
@Tag(name = "Clientes", description = "Listado de clientes")
public class ClienteController {

    private final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Obtiene el listado de clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de clientes",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Cliente.class)))),
    })
    @GetMapping( value = "/Clientes", produces = "applicacion/json")
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
    @Operation(summary = "Obtiene un cliente a partir de su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El cliente existe", content = @Content(schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "404", description = "El cliente no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/Clientes/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable long id) {
        Cliente cliente = clienteService.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra el cliente", content = @Content(schema = @Schema(implementation = Cliente.class)))
    })
    @PostMapping("/Clientes")
    public ResponseEntity<Cliente> addCliente(@RequestBody Cliente cliente) {
        Cliente addedCliente = clienteService.addCliente(cliente);
        return new ResponseEntity<>(addedCliente, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica un cliente de la lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se ha modificado el cliente", content = @Content(schema = @Schema(implementation = Cliente.class))),
            @ApiResponse(responseCode = "404", description = "El cliente no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping("/Clientes/{id}")
    public ResponseEntity<Cliente> modifyCliente(@PathVariable long id, @RequestBody Cliente newCliente) {
        Cliente cliente = clienteService.modifyCliente(id, newCliente);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina el cliente", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "El cliente no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
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
