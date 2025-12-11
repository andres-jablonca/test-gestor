package com.gestor_disp.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.gestor_disp.model.Trabajador;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Slf4j
@Service
@RequiredArgsConstructor
public class JasperReportService {

    private final ConcurrentHashMap<String, JasperReport> compiledReports = new ConcurrentHashMap<>();

    // Reporte de trabajadores usando tu JRXML
    public byte[] generarReporteTrabajadores(List<Trabajador> trabajadores) {
        // Datasource de la tabla (parameter "Data")
        JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(trabajadores != null ? trabajadores : List.of());

        Map<String, Object> params = new HashMap<>();
        // IMPORTANTE: mismo nombre que en el JRXML
        params.put("Data", dataSource);

        // El datasource principal del reporte es vacío
        return exportPdf("trabajadores.jasper", params, new JREmptyDataSource());
    }

    private byte[] exportPdf(String templateName, Map<String, Object> params, JRDataSource dataSource) {
        try {
            JasperPrint report = JasperFillManager.fillReport(loadReport(templateName), params, dataSource);
            return JasperExportManager.exportReportToPdf(report);
        } catch (JRException e) {
            log.error("No se pudo generar el reporte {}: {}", templateName, e.getMessage());
            throw new IllegalStateException("No se pudo generar el reporte " + templateName, e);
        }
    }

    private JasperReport loadReport(String templateName) {
        return compiledReports.computeIfAbsent(templateName, name -> {
            String jasperPath = "/jasper/" + name; // ej: /jasper/trabajadores.jasper
            try (InputStream is = getClass().getResourceAsStream(jasperPath)) {
                if (is == null) {
                    throw new IllegalStateException("No se encontró el jasper precompilado: " + jasperPath);
                }
                return (JasperReport) JRLoader.loadObject(is);
            } catch (IOException | JRException e) {
                throw new IllegalStateException("No se pudo cargar el jasper: " + jasperPath, e);
            }
        });
    }
}
