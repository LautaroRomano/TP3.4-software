package com.example.clinicaBDD;

import com.example.clinicaBDD.app.SistemaClinica;
import com.example.clinicaBDD.dominio.Diagnostico;
import com.example.clinicaBDD.dominio.Doctor;
import com.example.clinicaBDD.dominio.Paciente;
import com.example.clinicaBDD.repositorio.RepositorioPaciente;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CrearUnDiagnosticoStepDefinitions {

    private Doctor doctor;
    private Paciente pacienteSeleccionado;
    private Diagnostico diagnosticoSeleccionado;
    private RepositorioPaciente repositorioPaciente;

    @Before
    public void setUp(){
        this.repositorioPaciente = mock(RepositorioPaciente.class);
        //this.sistemaClinica = new SistemaClinica(repositorioPacientes);
    }

    @Given("El medico {string} esta autenticado en el sistema")
    public void elMedicoEstaAutenticadoEnElSistema(String nombreDoctor) {
        doctor = new Doctor(nombreDoctor);
    }

    @And("selecciona el paciente {string}")
    public void seleccionaElPaciente(String dniPaciente) throws Exception {
        Paciente newPaciente = new Paciente("Felipe Rocha",dniPaciente, List.of("Angina"));
        when(repositorioPaciente.buscarPaciente(dniPaciente)).thenReturn(Optional.of(newPaciente));

        pacienteSeleccionado = repositorioPaciente.buscarPaciente(dniPaciente).orElseThrow(() -> new Exception("El paciente  no existe en el sistema"));
    }

    @When("El médico selecciona nuevo diagnóstico")
    public void elMedicoSeleccionaNuevoDiagnostico() {
    }

    @And("elige un diagnóstico {string}")
    public void eligeUnDiagnostico(String nombreDiagnostico) {
        diagnosticoSeleccionado = new Diagnostico(nombreDiagnostico);
    }

    @Then("se guarda el nuevo diagnóstico")
    public void seGuardaElNuevoDiagnosticoYLoAsociaALaHistoriaClinicaDelPaciente() {
        pacienteSeleccionado.agregarDiagnostico(diagnosticoSeleccionado.getNombre());
        repositorioPaciente.actualizarPaciente(pacienteSeleccionado);
    }

    @And("lo asocia a la historia clínica del paciente")
    public void loAsociaALaHistoriaClinicaDelPaciente() {
        Optional<Paciente> pacienteOptional = repositorioPaciente.buscarPaciente(pacienteSeleccionado.getDni());
        assertThat(pacienteOptional.isEmpty()).isFalse();

        Paciente pacienteResultante = pacienteOptional.get();

        assertThat(pacienteResultante.tieneDiagnostico(diagnosticoSeleccionado.getNombre()))
                .isTrue();

        verify(repositorioPaciente, times(1)).actualizarPaciente(any());
    }
}
