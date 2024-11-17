Feature: Crear un nuevo diagnóstico

  Como médico, quiero poder crear un nuevo diagnóstico en la historia clínica de un paciente.

  Scenario Outline:  Crear un diagnóstico
    Given El medico "Lautaro Romano" esta autenticado en el sistema
    And selecciona el paciente "12345678"
    When El médico selecciona nuevo diagnóstico
    And elige un diagnóstico "<diagnostico>"
    Then se guarda el nuevo diagnóstico
    And lo asocia a la historia clínica del paciente


    Examples:
      | diagnostico         |
      | Tuberculosis miliar |
      | Angina              |
      | Dengue              |
