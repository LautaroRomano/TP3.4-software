package com.example.clinicaBDD.app;

import com.example.clinicaBDD.dominio.Doctor;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClinicaRest {

    private SistemaClinica sistemaClinica;
    private Doctor doctorPrueba = new Doctor("Dr prueba"); //En un futuro este doctor viene en la sesion

    public ClinicaRest(SistemaClinica sistemaClinica) {
        this.sistemaClinica = sistemaClinica;
    }

    @PostMapping("/paciente/{dniPaciente}/diagnostico/{nombreDiagnostico}/evolucion")
    public ResponseEntity<JsonNode> agregarEvolucion(@PathVariable String dniPaciente,
                                                     @PathVariable String nombreDiagnostico,
                                                     @RequestBody JsonNode json) {

        var paciente = this.sistemaClinica.agregarEvolucion(doctorPrueba,
                dniPaciente,
                nombreDiagnostico,
                JsonParser.informeDesde(json));

        return new ResponseEntity<>(JsonParser.pacienteAJson(paciente),HttpStatus.CREATED);
    }

    @GetMapping("/paciente/{dniPaciente}")
    public ResponseEntity<JsonNode> buscarPaciente(@PathVariable String dniPaciente) {
        var paciente = this.sistemaClinica.buscarPaciente(dniPaciente);

        return new ResponseEntity<>(JsonParser.pacienteAJson(paciente),HttpStatus.OK);
    }
}
