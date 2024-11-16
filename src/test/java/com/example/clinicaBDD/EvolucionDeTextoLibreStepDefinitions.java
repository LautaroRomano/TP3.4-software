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

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class EvolucionDeTextoLibreStepDefinitions {

    private Doctor doctor;
    private String dniPaciente;
    private Paciente pacienteResultante;
    private String informe;
    private String diagnosticoElegido;
    //controlador de fachada
    private SistemaClinica sistemaClinica;
    private RepositorioPaciente repositorioPacientes;

    @Before
    public void setUp(){
        this.repositorioPacientes = mock(RepositorioPaciente.class);
        this.sistemaClinica = new SistemaClinica(repositorioPacientes);
    }

    @Given("el médico {string} ha iniciado sesión")
    public void elMedicoHaaIniciadoSesion(String nombreDoctor) {
        doctor = new Doctor(nombreDoctor);
    }

    @And("ha buscado la historia clínica del paciente {string} que posee los siguientes diagnósticos:")
    public void haBuscadoLaHistoriaClinicaDelPacienteQuePoseeLosSiguientesDiagnosticos(String dniPaciente, List<String> diagnosticos) {
        Paciente paciente = new Paciente(dniPaciente, "Jhon Doe", diagnosticos);
        this.dniPaciente = dniPaciente;

        when(repositorioPacientes.buscarPaciente(dniPaciente)).thenReturn(Optional.of(paciente));
    }

    @When("el doctor escribe para el paciente previamente buscado un informe sobre el diagnóstico {string} que dice {string}")
    public void elDoctorEscribeParaElPacientePreviamenteBuscadoUnInformeSobreElDiagnosticoQueDice(String nombreDiagnostico, String informe) {
        diagnosticoElegido = nombreDiagnostico;
        this.informe = informe;
    }

    @And("el doctor guarda la evolución.")
    public void elDoctorGuardaLaEvolucion() {
        pacienteResultante = sistemaClinica.agregarEvolucion(doctor,dniPaciente,diagnosticoElegido,informe);
    }

    @Then("se registra la evolución en la historia clínica del paciente con el diagnóstico {string}, la descripción {string} y el médico {string}.")
    public void seRegistraLaEvolucionEnLaHistoriaClinicaDelPacienteConElDiagnosticoLaDescripcionYElMedico(String nombreDiagnostico, String informe,String medico) {
        Diagnostico diagnostico = pacienteResultante.buscarDiagnostico(nombreDiagnostico);
        assertThat(diagnostico.tieneEvolucion(doctor,informe)).isTrue();
        verify(repositorioPacientes,times(1)).actualizarPaciente(any());
    }

}
