Feature: Calificación de matrículas
  Como docente
  Quiero registrar la nota de una matrícula
  Para que el historial del alumno se actualice

  Scenario Outline: El docente registra la nota y el historial se actualiza
    Given que el alumno "i202610005" consulta sus matriculas
    And la matricula pagada del curso "PI5620" queda registrada
    When el docente "D0001" califica esa matricula con la nota <nota>
    Then el codigo de respuesta es 200
    And la condicion de la matricula es "<condicion>"
    When el alumno "i202610005" consulta su historial
    Then el codigo de respuesta es 200
    And los creditos aprobados son <creditosAprobados>
    And el promedio ponderado es <promedio>
    And la respuesta cumple el esquema "historial.json"

    Examples:
      | nota | condicion   | creditosAprobados | promedio |
      | 16   | APROBADO    | 13                | 15.69    |
      | 12.5 | DESAPROBADO | 7                 | 14.08    |

  Scenario: Un docente que no dicta el curso intenta registrar la nota
    Given que el alumno "i202610005" consulta sus matriculas
    And la matricula pagada del curso "PI5620" queda registrada
    When el docente "D0002" califica esa matricula con la nota 16
    Then el codigo de respuesta es 403
    And el codigo de error es "ACCESO_DENEGADO"
    And la respuesta cumple el esquema "error.json"

  Scenario: No se puede calificar una matrícula que no está pagada
    Given que el alumno "i202610005" consulta sus matriculas
    And la matricula del curso "PS5511" queda registrada
    When el docente "D0001" califica esa matricula con la nota 16
    Then el codigo de respuesta es 409
    And el codigo de error es "MATRICULA_NO_PAGADA"
    And la respuesta cumple el esquema "error.json"