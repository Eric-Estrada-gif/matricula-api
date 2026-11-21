package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.en.When;
import pe.edu.cibertec.matricula_api.screenplay.task.CerrarSesion;
import pe.edu.cibertec.matricula_api.screenplay.task.ConsultarPerfil;
import pe.edu.cibertec.matricula_api.screenplay.task.IniciarSesion;
import pe.edu.cibertec.matricula_api.screenplay.task.RenovarToken;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class SeguridadStepDefinitions {

    @When("inicia sesion con el usuario {string} y la clave {string}")
    public void iniciaSesion(String usuario, String clave) {
        theActorInTheSpotlight().attemptsTo(IniciarSesion.conCredenciales(usuario, clave));
    }

    @When("renueva el token")
    public void renuevaElToken() {
        theActorInTheSpotlight().attemptsTo(RenovarToken.actual());
    }

    @When("vuelve a usar el refresh token original")
    public void reutilizaElRefreshOriginal() {
        theActorInTheSpotlight().attemptsTo(RenovarToken.original());
    }

    @When("cierra la sesion")
    public void cierraLaSesion() {
        theActorInTheSpotlight().attemptsTo(CerrarSesion.actual());
    }

    @When("consulta su perfil")
    public void consultaSuPerfil() {
        theActorInTheSpotlight().attemptsTo(ConsultarPerfil.propio());
    }

    @When("consulta su perfil con el access token nuevo")
    public void consultaSuPerfilConElTokenNuevo() {
        theActorInTheSpotlight().attemptsTo(ConsultarPerfil.propio());
    }

    @When("intenta listar los usuarios como administrador")
    public void intentaListarUsuarios() {
        theActorInTheSpotlight().attemptsTo(ConsultarPerfil.deAdministracion());
    }
}
