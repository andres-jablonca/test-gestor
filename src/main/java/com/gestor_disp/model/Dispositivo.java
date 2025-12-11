package com.gestor_disp.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dispositivos")
public class Dispositivo {

    @Id
    @GeneratedValue(strategy=jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String codigoIdentificador;

    private String tipoDispositivo;
    private String marca;
    private String modelo;

    private String numeroTelefono;

    private String precio;

    private LocalDate fechaAdquisicion;

    @ManyToOne
    @JoinColumn(name = "trabajador_asociado_rut")
    @JsonBackReference
    private Trabajador trabajadorAsociado;

}
