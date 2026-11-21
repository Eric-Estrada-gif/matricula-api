Feature: : Matricula de cursos
  Como alumno de CIBERTEC
  Quiero matricularme en mis cursos
  Para llevarlos en el ciclo

  Scenario: Matricula valida con descuento por promedio y anulacion
    Given  que Ana consulta la API de matricula
    When se matricula con el codigo "i202610001" en el curso "PS3511"
    Then el codigo de respuesta es 201
    And el monto de la matricula es 288,0
    And el estado de la matricula es "REGISTRADA"
    When anula es matricula
    Then el codigo de respuesta es 200
    And el estado de la matricula es "ANULADA"

  Scenario Outline: Matricula rechazada por <motivo>
    Given  que Ana consulta la API de matricula
    When se matricula por codigo "<alumno>" en el curso "<curso>"
    Then el codigo de respuesta es <status>
    And el codigo de error es "<codigo>"
    Examples:
      | motivo             | alumno     | curso  | status | codigo               |
      | deuda              | i202610004 | PS5511 | 422    | ALUMNO_CON_DEUDA     |
      | duplicada          | i202610003 | IA5513 | 409    | MATRICULA_DUPLICADA  |
      | falta de vacantes  | i202610001 | IA5513 | 409    | SIN_VACANTES         |
      | cruce de horario   | i202610002 | QA5517 | 409    | CRUCE_HORARIO        |
      | límite de créditos | i202610005 | TS5514 | 422    | LIMITE_CREDITOS      |
      | alumno inexistente | i999999999 | PS5511 | 404    | ALUMNO_NO_ENCONTRADO |


    Scenario: Cotizar el curso
    When cotiza el curso "DM5512"
    Then el codigo de respuesta 200
    And los creditos son 4
    And el subtotal es 480
    And el descuento es 0
    And el total es 480

    Scenario: Matricularse y pagar el curso
    When se matricula en el curso "DM5512"
    Then el codigo de respuesta es 201
    And el estado de la matricula es "REGISTRADA"
    And el monto de la matricula es 480
    When paga la matricula con el metodo "TRANSFERENCIA"
    Then el codigo de respuesta es 200
    And el estado de la matricula es "PAGADA"
    And la fecha de apgo tiene valor
    And el pago de la matricula tiene el metodo "TRANSFERENCIA" y un numero de operacion "OP-000016"

    Scenario: No puede matricularse sin el prerrequisito
    When se matricula en el curso "QA5517"
    Then el codigo de respuesta es 422
    And el codigo de error es "PRERREQUISITO_PENDIENTE"