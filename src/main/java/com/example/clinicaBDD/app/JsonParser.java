package com.example.clinicaBDD.app;

import com.example.clinicaBDD.dominio.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String informeDesde(JsonNode json) {
        return json.get("informe").asText("");
    }

    public static JsonNode pacienteAJson(Paciente paciente) {
        ObjectNode json = mapper.createObjectNode();
        json.put("nombre", paciente.getNombre());
        json.put("dni", paciente.getDni());
        json.set("historiaClinica", historiaClinicaAJson(paciente.getHistoriaClinica()));

        return json;
    }

    public static JsonNode historiaClinicaAJson(HistoriaClinica historiaClinica) {
        ObjectNode json = mapper.createObjectNode();
        ArrayNode diagnosticosArray = mapper.createArrayNode();

        historiaClinica.getDiagnosticos()
                .forEach(diagnostico -> diagnosticosArray.add(diagnosticosAJson(diagnostico)));

        json.set("diagnosticos", diagnosticosArray);

        return json;
    }

    public static JsonNode diagnosticosAJson(Diagnostico diagnostico) {
        ObjectNode json = mapper.createObjectNode();

        ArrayNode evolucionesArray = mapper.createArrayNode();

        diagnostico.getEvoluciones()
                .forEach(evolucion -> evolucionesArray.add(evolucionAJson(evolucion)));


        json.put("nombre", diagnostico.getNombre());
        json.set("evoluciones", evolucionesArray);

        return json;
    }

    public static JsonNode evolucionAJson(Evolucion evolucion) {
        ObjectNode json = mapper.createObjectNode();
        json.put("informe", evolucion.getInforme());
        json.set("doctor", doctorAJson(evolucion.getDoctor()));

        return json;
    }

    public static JsonNode doctorAJson(Doctor doctor) {
        ObjectNode json = mapper.createObjectNode();
        json.put("nombre", doctor.getNombre());

        return json;
    }
}
