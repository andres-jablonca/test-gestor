package com.gestor_disp.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trabajadores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trabajador {

    @Id
    private String rut;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String primerNombre;
    private String segundoNombre;
    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellidoPaterno;
    private String apellidoMaterno;

    @Email
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    @NotBlank(message = "El número de teléfono no puede estar vacío")
    private String numeroTelefono;

    private String direccion;
    private String comuna;
    private String nacionalidad;

    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = "trabajadorAsociado")
    @JsonManagedReference
    private List<Dispositivo> dispositivosAsociado;
}
