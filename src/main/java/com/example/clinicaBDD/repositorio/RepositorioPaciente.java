package com.example.clinicaBDD.repositorio;

import com.example.clinicaBDD.dominio.Paciente;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class RepositorioPaciente {

    private Map<String, Paciente> pacientes;

    public RepositorioPaciente() {
        pacientes = new HashMap<>();
        cargarPacientes();
    }

    public Optional<Paciente> buscarPaciente(String dniPaciente) {
        return Optional.ofNullable(pacientes.get(dniPaciente));
    }

    public void actualizarPaciente(Paciente paciente) {

    }

    public void cargarPacientes() {
        pacientes.put("43846366",new Paciente("Lautaro Romano","43846366", List.of("Angina")));
    }
}
