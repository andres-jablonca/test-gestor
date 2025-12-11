package com.gestor_disp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gestor_disp.model.Dispositivo;
import com.gestor_disp.repository.DispositivoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DispositivoService {

    private final DispositivoRepository dispositivoRepository;

    public List<Dispositivo> obtenerDispositivos() {
        return dispositivoRepository.findAll();
    }

    public Dispositivo obtenerDispositivo(Long id) {
        if (!dispositivoRepository.existsById(id)) {
            throw new IllegalArgumentException("El ID " + id + " no se encuentra registrado.");
        }
        return dispositivoRepository.findById(id).get();
    }

    public Dispositivo crearDispositivo(Dispositivo dispositivo) {
        dispositivo.setId(null);
        return dispositivoRepository.save(dispositivo);
    }

    public Dispositivo actualizarDispositivo(Long id, Dispositivo dispositivoActualizado) {
        
        Dispositivo dispositivoExistente = obtenerDispositivo(id);

        dispositivoExistente.setCodigoIdentificador(dispositivoActualizado.getCodigoIdentificador());
        dispositivoExistente.setTipoDispositivo(dispositivoActualizado.getTipoDispositivo());
        dispositivoExistente.setMarca(dispositivoActualizado.getMarca());
        dispositivoExistente.setModelo(dispositivoActualizado.getModelo());
        dispositivoExistente.setNumeroTelefono(dispositivoActualizado.getNumeroTelefono());
        dispositivoExistente.setPrecio(dispositivoActualizado.getPrecio());
        dispositivoExistente.setFechaAdquisicion(dispositivoActualizado.getFechaAdquisicion());
        dispositivoExistente.setTrabajadorAsociado(dispositivoActualizado.getTrabajadorAsociado());

        return dispositivoRepository.save(dispositivoExistente);
    }

    public void eliminarDispositivo(Long id) {
        if (!dispositivoRepository.existsById(id)) {
            throw new IllegalArgumentException("El ID " + id + " no se encuentra registrado.");
        }
        dispositivoRepository.deleteById(id);
    }

}