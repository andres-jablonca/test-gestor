package com.gestor_disp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gestor_disp.model.Trabajador;
import com.gestor_disp.repository.TrabajadorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrabajadorService {
    
    private final TrabajadorRepository trabajadorRepository;
    private final JasperReportService jasperReportService;

    public List<Trabajador> obtenerTrabajadores() {
        return trabajadorRepository.findAll();
    }

    public Trabajador obtenerTrabajadorPorRut(String rut) {
        if (!trabajadorRepository.existsById(rut)) {
            throw new IllegalArgumentException("El RUT " + rut + " no se encuentra registrado.");
        }
        return trabajadorRepository.findById(rut).get();
    }

    public Trabajador crearTrabajador(Trabajador trabajador) {
        if (trabajadorRepository.existsById(trabajador.getRut())) {
            throw new IllegalArgumentException("El RUT " + trabajador.getRut() + " ya se encuentra registrado.");
        }
        return trabajadorRepository.save(trabajador);
    }

    public Trabajador actualizarTrabajador(String rut, Trabajador trabajadorActualizado) {
        if (!trabajadorRepository.existsById(rut)) {
            throw new IllegalArgumentException("El RUT " + rut + " no se encuentra registrado.");
        }
        Trabajador trabajadorExistente = obtenerTrabajadorPorRut(rut);
        trabajadorExistente.setPrimerNombre(trabajadorActualizado.getPrimerNombre());
        trabajadorExistente.setSegundoNombre(trabajadorActualizado.getSegundoNombre());
        trabajadorExistente.setApellidoPaterno(trabajadorActualizado.getApellidoPaterno());
        trabajadorExistente.setApellidoMaterno(trabajadorActualizado.getApellidoMaterno());
        trabajadorExistente.setEmail(trabajadorActualizado.getEmail());
        trabajadorExistente.setNumeroTelefono(trabajadorActualizado.getNumeroTelefono());
        trabajadorExistente.setDireccion(trabajadorActualizado.getDireccion());
        trabajadorExistente.setComuna(trabajadorActualizado.getComuna());
        trabajadorExistente.setNacionalidad(trabajadorActualizado.getNacionalidad());

        return trabajadorRepository.save(trabajadorExistente);
    }

    public void eliminarTrabajador(String rut) {
        if (!trabajadorRepository.existsById(rut)) {
            throw new IllegalArgumentException("El RUT " + rut + " no se encuentra registrado.");
        }
        trabajadorRepository.deleteById(rut);
    }

    public byte[] generarPdfTrabajadores() {
        List<Trabajador> trabajadores = obtenerTrabajadores();
        return jasperReportService.generarReporteTrabajadores(trabajadores);
    }
}
