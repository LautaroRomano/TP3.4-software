package com.example.clinicaBDD.dominio;

import lombok.Getter;

@Getter
public class Evolucion {
    private String informe;
    private Doctor doctor;

    public Evolucion(String informe, Doctor doctor) {
        this.informe = informe;
        this.doctor = doctor;
    }

    public Boolean tiene(Doctor doctor,String informe) {
            return  this.informe.equals(informe) && this.doctor.equals(doctor);
    }
}
