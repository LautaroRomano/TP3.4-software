package com.example.clinicaBDD.dominio;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Diagnostico {
    private String nombre;
    private List<Evolucion> evoluciones;

    public Diagnostico(String nombre) {
        this.nombre = nombre;
        this.evoluciones = new ArrayList<>();
    }

    public Boolean tieneNombre(String nombre) {
        return this.nombre.equals(nombre);
    }

    public Boolean tieneEvolucion(Doctor doctor, String informe) {
        return evoluciones
                .stream()
                .anyMatch(evolucion -> evolucion.tiene(doctor, informe));
    }

    public void agregarEvolucion(Doctor doctor, String informe) {
        Evolucion evolucion = new Evolucion(informe, doctor);
        evoluciones.add(evolucion);
    }
}
