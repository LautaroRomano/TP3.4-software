package com.example.clinicaBDD;

import com.example.clinicaBDD.dominio.Doctor;
import com.example.clinicaBDD.dominio.Paciente;
import com.example.clinicaBDD.repositorio.RepositorioPaciente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CrearNuevoDiagnosticoTests {

    @Mock
    private RepositorioPaciente repositorioPaciente;

    Doctor doctor = new Doctor("Dr. Romano");

    @Test
    void testCrearDiagnosticoConExito() {
        //Given El medico "Dr. Romano" esta autenticado en el sistema
        Assertions.assertNotNull(doctor);
        Assertions.assertEquals("Dr. Romano", doctor.getNombre());

        //And selecciona el paciente "43123456"
        String dniPaciente = "43123456";
        Paciente newPaciente = new Paciente("Felipe Rocha",dniPaciente, List.of("Angina","Dengue"));

        //When El médico selecciona nuevo diagnóstico
        //And elige un diagnóstico "Tuberculosis miliar"
        String nombreDiagnostico = "Tuberculosis miliar";


        //Then se guarda el nuevo diagnóstico
        //And lo asocia a la historia clínica del paciente
        newPaciente.agregarDiagnostico(nombreDiagnostico);
        repositorioPaciente.actualizarPaciente(newPaciente);


        // Configurar el mock
        when(repositorioPaciente.buscarPaciente(dniPaciente)).thenReturn(Optional.of(newPaciente));

        // Ejecutar el método a probar
        Paciente pacienteResult = repositorioPaciente.buscarPaciente(dniPaciente).orElseThrow(()->new RuntimeException("Paciente no encontrado"));

        // Verificar el resultado
        Assertions.assertNotNull(pacienteResult);
        Assertions.assertNotNull(pacienteResult.getHistoriaClinica().buscarDiagnostico(nombreDiagnostico));

        // Verificar interacción con el mock
        verify(repositorioPaciente).buscarPaciente(dniPaciente);
    }

    @Test
    void testNoAgregarDiagnosticoInvalido() {
        // Given El medico "Dr. Romano" está autenticado en el sistema
        Assertions.assertNotNull(doctor);
        Assertions.assertEquals("Dr. Romano", doctor.getNombre());

        // And selecciona el paciente "43123456"
        String dniPaciente = "43123456";
        Paciente newPaciente = new Paciente("Felipe Rocha", dniPaciente, List.of("Angina", "Dengue"));

        // When El médico selecciona nuevo diagnóstico
        // And elige un diagnóstico no válido (vacío o null)
        String diagnosticoInvalido = null;

        // Verificar que no se agrega un diagnóstico inválido
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            newPaciente.agregarDiagnostico(diagnosticoInvalido);
        });

        // Configurar el mock para verificar la ausencia de cambios
        when(repositorioPaciente.buscarPaciente(dniPaciente)).thenReturn(Optional.of(newPaciente));

        // Ejecutar el método a probar
        Paciente pacienteResult = repositorioPaciente.buscarPaciente(dniPaciente).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // Verificar que no se haya agregado el diagnóstico inválido
        Assertions.assertFalse(pacienteResult.getHistoriaClinica().getDiagnosticos().contains(diagnosticoInvalido));

        // Verificar interacción con el mock
        verify(repositorioPaciente).buscarPaciente(dniPaciente);
    }

    @Test
    void testAsociarDiagnosticoAlPacienteCorrecto() {
        // Given El médico "Dr. Romano" está autenticado en el sistema
        Assertions.assertNotNull(doctor);
        Assertions.assertEquals("Dr. Romano", doctor.getNombre());

        // And selecciona los pacientes "43123456" y "12345678"
        String dniPaciente1 = "43123456";
        Paciente paciente1 = new Paciente("Felipe Rocha", dniPaciente1, List.of("Angina"));

        String dniPaciente2 = "12345678";
        Paciente paciente2 = new Paciente("Ana Gómez", dniPaciente2, List.of("Dengue"));

        // When El médico selecciona nuevo diagnóstico
        // And elige un diagnóstico "Tuberculosis miliar" para el paciente "43123456"
        String nombreDiagnostico = "Tuberculosis miliar";
        paciente1.agregarDiagnostico(nombreDiagnostico);

        // Configurar el mock
        when(repositorioPaciente.buscarPaciente(dniPaciente1)).thenReturn(Optional.of(paciente1));
        when(repositorioPaciente.buscarPaciente(dniPaciente2)).thenReturn(Optional.of(paciente2));

        // Ejecutar el método a probar
        Paciente pacienteResult1 = repositorioPaciente.buscarPaciente(dniPaciente1).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        Paciente pacienteResult2 = repositorioPaciente.buscarPaciente(dniPaciente2).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // Verificar que el diagnóstico se asoció al paciente correcto
        boolean diagnosticoEnPaciente1 = pacienteResult1.getHistoriaClinica().getDiagnosticos()
                .stream()
                .anyMatch(diagnostico -> diagnostico.getNombre().equalsIgnoreCase(nombreDiagnostico));
        boolean diagnosticoEnPaciente2 = pacienteResult2.getHistoriaClinica().getDiagnosticos()
                .stream()
                .anyMatch(diagnostico -> diagnostico.getNombre().equalsIgnoreCase(nombreDiagnostico));

        Assertions.assertTrue(diagnosticoEnPaciente1, "El diagnóstico debería estar asociado al paciente 1.");
        Assertions.assertFalse(diagnosticoEnPaciente2, "El diagnóstico no debería estar asociado al paciente 2.");

        // Verificar interacción con los mocks
        verify(repositorioPaciente).buscarPaciente(dniPaciente1);
        verify(repositorioPaciente).buscarPaciente(dniPaciente2);
    }



}
