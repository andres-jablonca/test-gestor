package com.gestor_disp.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gestor_disp.model.Trabajador;
import com.gestor_disp.service.TrabajadorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trabajadores")
@RequiredArgsConstructor
public class TrabajadorController {

    private final TrabajadorService trabajadorService;

    @GetMapping
    public List<Trabajador> listTrabajadores() {
        return trabajadorService.obtenerTrabajadores();
    }

    @GetMapping("/{rut}")
    public Trabajador getTrabajadorByRut(@PathVariable String rut) {
        return trabajadorService.obtenerTrabajadorPorRut(rut);
    }

    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Trabajador crearTrabajador(@RequestBody Trabajador trabajador) {
        return trabajadorService.crearTrabajador(trabajador);
    }

    @PutMapping("/{rut}")
    public Trabajador actualizarTrabajador(@PathVariable String rut, @RequestBody Trabajador trabajadorActualizado) {
        return trabajadorService.actualizarTrabajador(rut, trabajadorActualizado);
    }

    @DeleteMapping("/{rut}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void eliminarTrabajador(@PathVariable String rut) {
        trabajadorService.eliminarTrabajador(rut);
    }

    @GetMapping("/{rut}/anexo-contrato")
    public ResponseEntity<byte[]> exportarPdfAnexo(@PathVariable String rut) {
        byte[] pdf = trabajadorService.generarPdfAnexoContrato(rut);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=anexo_" + rut + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/{rut}/dispositivos")
    public ResponseEntity<byte[]> exportarPdfDispositivos(@PathVariable String rut) {
        byte[] pdf = trabajadorService.generarPdfDispositivosTrabajador(rut);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=dispositivos_" + rut + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
