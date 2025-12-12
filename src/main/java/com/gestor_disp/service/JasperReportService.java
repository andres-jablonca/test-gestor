package com.gestor_disp.service;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Slf4j
@Service
@RequiredArgsConstructor
public class JasperReportService {

    private final ConcurrentHashMap<String, JasperReport> reportesCompilados = new ConcurrentHashMap<>();

    public byte[] generateReport(Map<String, Object> params, String jrxmlPath) {
        JRDataSource dataSource = new JREmptyDataSource();

        try {
            JasperReport reporte = cargarReporte(jrxmlPath);
            JasperPrint print = JasperFillManager.fillReport(reporte, params, dataSource);
            return JasperExportManager.exportReportToPdf(print);
        } catch (JRException e) {
            log.error("No se pudo generar el reporte {}: {}", jrxmlPath, e.getMessage(), e);
            throw new IllegalStateException("No se pudo generar el reporte: " + jrxmlPath, e);
        }
    }

    private JasperReport cargarReporte(String jrxmlPath) {
        return reportesCompilados.computeIfAbsent(jrxmlPath, path -> {
            try (InputStream is = getClass().getResourceAsStream(path)) {
                if (is == null) {
                    throw new IllegalStateException("No se encontró el JRXML en classpath: " + path);
                }
                return JasperCompileManager.compileReport(is);
            } catch (Exception e) {
                log.error("Fallo compilando JRXML {}: {}", path, e.getMessage(), e);
                throw new IllegalStateException("No se pudo compilar el JRXML: " + path, e);
            }
        });
    }
}
