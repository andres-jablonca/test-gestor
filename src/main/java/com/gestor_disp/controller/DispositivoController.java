package com.gestor_disp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gestor_disp.model.Dispositivo;
import com.gestor_disp.service.DispositivoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dispositivos")
@RequiredArgsConstructor
public class DispositivoController {

    private final DispositivoService dispositivoService;

    @GetMapping
    public List<Dispositivo> listDispositivos() {
        return dispositivoService.obtenerDispositivos();
    }    

    @GetMapping("/{id}")
    public Dispositivo getDispositivoById(@PathVariable Long id) {
        return dispositivoService.obtenerDispositivo(id);
    }

    @PostMapping
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Dispositivo crearDispositivo(@org.springframework.web.bind.annotation.RequestBody Dispositivo dispositivo) {
        return dispositivoService.crearDispositivo(dispositivo);
    }

    @PutMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Dispositivo actualizarDispositivo(@PathVariable Long id, @org.springframework.web.bind.annotation.RequestBody Dispositivo dispositivoActualizado) {
        return dispositivoService.actualizarDispositivo(id, dispositivoActualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void eliminarDispositivo(@PathVariable Long id) {
        dispositivoService.eliminarDispositivo(id);
    }
}
