package com.example.clinicaBDD;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HelloStepDefinitions {

    private final List<String> mensajes = new ArrayList<>();

    @When("digo {string}")
    public void digo(String mensaje) {
        mensajes.add(mensaje);
    }

    @Then("se muestra el mensaje {string}")
    public void seMuestraElMensaje(String mensaje) {
        assertThat(mensajes).contains(mensaje);
    }
}
