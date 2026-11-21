Feature: Seguridad de la sesión
  Como usuario de la API de matrícula
  Quiero que mi sesión esté protegida
  Para evitar accesos no autorizados

  Scenario: Rotación del refresh token
    When inicia sesion con el usuario "i202610006" y la clave "Cibertec2026"
    And renueva el token
    Then el codigo de respuesta es 200
    When vuelve a usar el refresh token original
    Then el codigo de respuesta es 401
    And el codigo de error es "REFRESH_REUTILIZADO"
    When consulta su perfil con el access token nuevo
    Then el codigo de respuesta es 401
    And el codigo de error es "TOKEN_INVALIDO"

  Scenario: Cierre de sesión
    When inicia sesion con el usuario "i202610006" y la clave "Cibertec2026"
    And cierra la sesion
    Then el codigo de respuesta es 204
    When consulta su perfil
    Then el codigo de respuesta es 401
    And el codigo de error es "TOKEN_INVALIDO"

  Scenario Outline: Bloqueo de cuenta por intentos fallidos
    When inicia sesion con el usuario "i202610004" y la clave "<clave>"
    Then el codigo de respuesta es <status>
    And el codigo de error es "<codigo>"

    Examples:
      | clave        | status | codigo                 |
      | incorrecta   | 401    | CREDENCIALES_INVALIDAS |
      | incorrecta   | 401    | CREDENCIALES_INVALIDAS |
      | incorrecta   | 401    | CREDENCIALES_INVALIDAS |
      | incorrecta   | 401    | CREDENCIALES_INVALIDAS |
      | incorrecta   | 401    | CREDENCIALES_INVALIDAS |
      | Cibertec2026 | 423    | CUENTA_BLOQUEADA       |

  Scenario: Alumno sin permiso de administrador
    When inicia sesion con el usuario "i202610006" y la clave "Cibertec2026"
    And intenta listar los usuarios como administrador
    Then el codigo de respuesta es 403
    And el codigo de error es "ACCESO_DENEGADO"