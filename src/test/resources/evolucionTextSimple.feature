Feature: Agregar una nueva evolución con diagnóstico previo.

  Para que se pueda diagnosticar al paciente,
  el médico
  quiere añadir una nueva evolución en la historia clínica del paciente eligiendo un diagnóstico previo.

  Background: El médico visualiza una historia clínica del paciente.
    Given el médico "Lautaro Romano" ha iniciado sesión
    And ha buscado la historia clínica del paciente "43846366" que posee los siguientes diagnósticos:
      | Angina    |
      | Gastritis |
      | Dengue    |

  Scenario Outline: El médico agrega una evolución con texto libre para un diagnóstico específico.
    When el doctor escribe para el paciente previamente buscado un informe sobre el diagnóstico "<diagnostico>" que dice "El paciente presenta los síntomas de una <diagnostico> viral"
    And el doctor guarda la evolución.
    Then se registra la evolución en la historia clínica del paciente con el diagnóstico "<diagnostico>", la descripción "El paciente presenta los síntomas de una <diagnostico> viral" y el médico "Lautaro Romano".

    Examples:
      | diagnostico |
      | Angina      |
      | Gastritis   |
      | Dengue      |
