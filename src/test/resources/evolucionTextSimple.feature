Feature: Crear un nuevo diagnóstico

  Como médico, quiero poder crear un nuevo diagnóstico en la historia clínica de un paciente.

  Background: El médico visualiza una historia clínica del paciente.
    Given el médico "Dr. Romano" ha iniciado sesión
    And ha buscado la historia clínica del paciente "43846366" que posee los siguientes diagnósticos:
      | Angina    |
      | Gastritis |
      | Dengue    |

  Scenario Outline: El médico agrega una evolución con texto libre para un diagnóstico específico.
    When el doctor escribe para el paciente previamente buscado un informe sobre el diagnóstico "<diagnostico>" que dice "El paciente presenta los síntomas de una <diagnostico> viral"
    And el doctor guarda la evolución.
    Then se registra la evolución en la historia clínica del paciente con el diagnóstico "<diagnostico>", la descripción "El paciente presenta los síntomas de una <diagnostico> viral" y el médico "Dr. Romano".

    Examples:
      | diagnostico |
      | Angina      |
      | Gastritis   |
      | Dengue      |
