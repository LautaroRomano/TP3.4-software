package com.example.clinicaBDD;

import com.example.clinicaBDD.app.SistemaClinica;
import com.example.clinicaBDD.dominio.Diagnostico;
import com.example.clinicaBDD.dominio.Doctor;
import com.example.clinicaBDD.dominio.HistoriaClinica;
import com.example.clinicaBDD.dominio.Paciente;
import com.example.clinicaBDD.repositorio.RepositorioPaciente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EvolucionTextoLibreTests {

    @Mock
    private RepositorioPaciente repositorioPaciente = new RepositorioPaciente();

    private final SistemaClinica sistemaClinica = new SistemaClinica(repositorioPaciente);

    Doctor doctor = new Doctor("Dr. Romano");

    @Test
    void testEvolucionTextoLibreExito() {

        // Configurar el mock
        Paciente newPaciente = new Paciente("Felipe Rocha", "43123456", List.of("Angina", "Gastritis", "Dengue"));
        when(repositorioPaciente.buscarPaciente("43123456")).thenReturn(Optional.of(newPaciente));

        //Given el médico "Dr. Romano" ha iniciado sesión
        Assertions.assertNotNull(doctor);
        Assertions.assertEquals("Dr. Romano", doctor.getNombre());

        //And ha buscado la historia clínica del paciente "43123456" que posee los siguientes diagnósticos:
        //      | Angina    |
        //      | Gastritis |
        //      | Dengue    |
        String dniPaciente = "43123456";
        Paciente paciente = repositorioPaciente.buscarPaciente(dniPaciente).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        //When el doctor escribe para el paciente previamente buscado un informe sobre el diagnóstico Angina que dice "El paciente presenta los síntomas de una Angina viral"
        String diagnosticoElegido = "Angina";
        String informe = "El paciente presenta los síntomas de una Angina viral";

        //And el doctor guarda la evolución.
        Paciente pacienteResultante = sistemaClinica.agregarEvolucion(doctor, paciente.getDni(), diagnosticoElegido, informe);
        repositorioPaciente.actualizarPaciente(pacienteResultante);


        //Then se registra la evolución en la historia clínica del paciente con el diagnóstico "Angina", la descripción "El paciente presenta los síntomas de una Angina viral" y el médico "Dr. Romano".
        Diagnostico diagnostico = pacienteResultante.buscarDiagnostico("Angina");
        assertThat(diagnostico.tieneEvolucion(doctor,informe)).isTrue();

        // Verificar interacción con el mock
        verify(repositorioPaciente,times(1)).actualizarPaciente(any());
    }

    @Test
    void testEvolucionConDiagnosticoInexistente() {
        // Configurar el mock
        Paciente newPaciente = new Paciente("Felipe Rocha", "43123456", List.of("Angina", "Gastritis", "Dengue"));
        when(repositorioPaciente.buscarPaciente("43123456")).thenReturn(Optional.of(newPaciente));

        // Given el médico "Dr. Romano" ha iniciado sesión
        Assertions.assertNotNull(doctor);
        Assertions.assertEquals("Dr. Romano", doctor.getNombre());

        //And ha buscado la historia clínica del paciente "43123456" que posee los siguientes diagnósticos:
        //      | Angina    |
        //      | Gastritis |
        //      | Dengue    |
        String dniPaciente = "43123456";
        Paciente paciente = repositorioPaciente.buscarPaciente(dniPaciente).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // When el doctor intenta escribir un informe sobre un diagnóstico inexistente "Diabetes"
        String diagnosticoInexistente = "Diabetes";
        String informe = "El paciente presenta síntomas de Diabetes tipo 1";

        // Then se lanza una excepción
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () ->
                sistemaClinica.agregarEvolucion(doctor, paciente.getDni(), diagnosticoInexistente, informe)
        );

        Assertions.assertEquals("Diagnostico no encontrado", exception.getMessage());

        // Verificar que no se actualizó al paciente
        verify(repositorioPaciente, never()).actualizarPaciente(any());
    }

    @Test
    void testEvolucionConInformeVacio() {
        // Configurar el mock
        Paciente newPaciente = new Paciente("Felipe Rocha", "43123456", List.of("Angina", "Gastritis"));
        when(repositorioPaciente.buscarPaciente("43123456")).thenReturn(Optional.of(newPaciente));

        // Given el médico "Dr. Romano" ha iniciado sesión
        Assertions.assertNotNull(doctor);
        Assertions.assertEquals("Dr. Romano", doctor.getNombre());

        // And ha buscado la historia clínica del paciente "43123456" que posee los diagnósticos:
        //      | Angina    |
        //      | Gastritis |
        String dniPaciente = "43123456";
        Paciente paciente = repositorioPaciente.buscarPaciente(dniPaciente).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // When el doctor intenta guardar una evolución con un informe vacío
        String diagnosticoElegido = "Angina";
        String informeVacio = "";

        // Then se lanza una excepción
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                sistemaClinica.agregarEvolucion(doctor, paciente.getDni(), diagnosticoElegido, informeVacio)
        );

        Assertions.assertEquals("El informe no puede estar vacío", exception.getMessage());

        // Verificar que no se actualizó al paciente
        verify(repositorioPaciente, never()).actualizarPaciente(any());
    }



}

