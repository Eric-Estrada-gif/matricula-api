package pe.edu.cibertec.matricula_api.screenplay.task;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class ConsultarPerfil implements Task {
    private final String ruta;

    public ConsultarPerfil(String ruta) {
        this.ruta = ruta;
    }

    public static ConsultarPerfil propio() {
        return new ConsultarPerfil("/api/auth/perfil");
    }

    public static ConsultarPerfil deAdministracion() {
        return new ConsultarPerfil("/api/usuarios");
    }

    @Override
    @Step("{0} consulta #ruta")
    public <T extends Actor> void performAs(T actor) {
        String access = actor.recall("accessToken");
        actor.attemptsTo(
                Get.resource(ruta)
                        .with(req -> req.header("Authorization", "Bearer " + access)));
    }
}
