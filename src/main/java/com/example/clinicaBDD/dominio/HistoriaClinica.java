package com.example.clinicaBDD.dominio;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class HistoriaClinica {
    private List<Diagnostico> diagnosticos;

    public HistoriaClinica(List<String> diagnosticos) {
        this.diagnosticos = diagnosticos
                .stream()
                .map(Diagnostico::new)
                .collect(Collectors.toList());
    }

    public Diagnostico buscarDiagnostico(String nombreDiagnostico) {
        return this.diagnosticos
                .stream()
                .filter(diagnostico -> diagnostico.tieneNombre(nombreDiagnostico))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Diagnostico no encontrado"));
    }

    public void agregarEvolucion(String diagnosticoElegido, Doctor doctor, String informe) {
        Diagnostico diagnostico = this.buscarDiagnostico(diagnosticoElegido);
        diagnostico.agregarEvolucion(doctor,informe);
    }
}
