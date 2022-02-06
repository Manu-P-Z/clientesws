package com.manu.Clientes.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "clientes")
public class Cliente {

    @Schema(description = "Identificador del cliente",example = "1",required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Nombre del cliente", example = "Dennis", required = true)
    @NotBlank
    @Column
    private String nombre;

    @Schema(description = "Apellidos del cliente", example = "Rivera")
    @Column
    private String apellidos;

    @Schema(description = "Categoría del cliente", example = "Particular",required = true)
    @NotBlank
    @Column
    private String category;

    @Schema(description = "Límite de crédito del cliente", example = "5400.0",defaultValue = "0.0")
    @Column
    @Min(value = 0)
    private float limite_credito;

    @Schema(description = "Fecha de registro del cliente", example = "2020-03-01")
    @Column(name = "fecha_creacion")
    private LocalDateTime creationDate;
}
