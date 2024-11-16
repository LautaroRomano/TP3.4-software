package com.example.clinicaBDD.dominio;

import lombok.Getter;

@Getter
public class Doctor {
    private String nombre;

    public Doctor(String nombre) {
        this.nombre = nombre;
    }
}
