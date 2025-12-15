package com.gestor_disp.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gestor_disp.model.Dispositivo;
import com.gestor_disp.model.Trabajador;
import com.gestor_disp.repository.TrabajadorRepository;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

    public byte[] generarPdfAnexoContrato(String rut) {
        Trabajador t = obtenerTrabajadorPorRut(rut);

        Map<String, Object> params = new HashMap<>();
        params.put("rut", t.getRut());
        params.put("primerNombre", t.getPrimerNombre());
        params.put("apellidoPaterno", t.getApellidoPaterno());
        params.put("apellidoMaterno", t.getApellidoMaterno());
        params.put("direccion", t.getDireccion());
        params.put("comuna", t.getComuna());
        params.put("nacionalidad", t.getNacionalidad());
        params.put("fechaNacimiento", java.sql.Date.valueOf(t.getFechaNacimiento()));
        params.put("empresa", "Finterra");
        params.put("empresaRut", "76.311.552-6");

        params.put("fechaEmision", new Date());

        List<Dispositivo> disp = (t.getDispositivosAsociado() != null) ? t.getDispositivosAsociado() : List.of();
        params.put("dispositivosDataSource", new JRBeanCollectionDataSource(disp));

        return jasperReportService.generateReport(params, "/jasper/anexo_contrato.jrxml");
    }

    public byte[] generarPdfDispositivosTrabajador(String rut) {
        Trabajador t = obtenerTrabajadorPorRut(rut);

        Map<String, Object> params = new HashMap<>();
        params.put("rut", t.getRut());
        params.put("primerNombre", t.getPrimerNombre());
        params.put("segundoNombre", t.getSegundoNombre());
        params.put("apellidoPaterno", t.getApellidoPaterno());
        params.put("apellidoMaterno", t.getApellidoMaterno());
        params.put("email", t.getEmail());

        List<Dispositivo> disp = (t.getDispositivosAsociado() != null) ? t.getDispositivosAsociado() : List.of();
        params.put("dispositivosDataSource", new JRBeanCollectionDataSource(disp));

        return jasperReportService.generateReport(params, "/jasper/trabajadores_dispositivos.jrxml");
    }
}
