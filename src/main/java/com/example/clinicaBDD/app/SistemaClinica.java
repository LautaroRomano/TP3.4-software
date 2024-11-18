package com.example.clinicaBDD.app;

import com.example.clinicaBDD.dominio.Doctor;
import com.example.clinicaBDD.dominio.Paciente;
import com.example.clinicaBDD.repositorio.RepositorioPaciente;
import org.springframework.stereotype.Service;

@Service
public class SistemaClinica {
    private final RepositorioPaciente repositorioPaciente;

    public SistemaClinica(RepositorioPaciente repositorioPaciente) {
        this.repositorioPaciente = repositorioPaciente;
    }

    public Paciente buscarPaciente(String dniPaciente){
        return repositorioPaciente.buscarPaciente(dniPaciente).orElseThrow(()->new RuntimeException("PAciente no encontrado"));
    }

    public Paciente agregarEvolucion(Doctor doctor, String dniPaciente, String diagnosticoElegido, String informe) {
        Paciente paciente = repositorioPaciente
                .buscarPaciente(dniPaciente)
                .orElseThrow(() -> new RuntimeException("Paciente inexistente"));
        if(informe.isBlank()) throw new IllegalArgumentException("El informe no puede estar vacío");
        paciente.agregarEvolucion(diagnosticoElegido,doctor,informe);
        repositorioPaciente.actualizarPaciente(paciente);
        return paciente;
    }
}
