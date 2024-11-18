package com.example.clinicaBDD.dominio;

import lombok.Getter;

import java.util.List;

@Getter
public class Paciente {
    private String nombre;
    private String dni;
    private HistoriaClinica historiaClinica;

    public Paciente(String nombre, String dni, List<String> diagnosticosPreexistentes) {
        this.nombre = nombre;
        this.dni = dni;
        this.historiaClinica = new HistoriaClinica(diagnosticosPreexistentes);
    }

    public Diagnostico buscarDiagnostico(String nombreDiagnostico){
        return this.historiaClinica.buscarDiagnostico(nombreDiagnostico);
    }

    public boolean tieneDiagnostico(String nombreDiagnostico){
        return this.historiaClinica.buscarDiagnostico(nombreDiagnostico).tieneNombre(nombreDiagnostico);
    }

    public void agregarEvolucion(String diagnosticoElegido, Doctor doctor, String informe) {
        historiaClinica.agregarEvolucion(diagnosticoElegido,doctor,informe);
    }

    public void agregarDiagnostico(String nombreDiagnostico) {
        if (nombreDiagnostico == null || nombreDiagnostico.isBlank()) {
            throw new IllegalArgumentException("El diagnóstico no puede ser nulo o vacío.");
        }

        historiaClinica.agregarDiagnostico(nombreDiagnostico);
    }
}
