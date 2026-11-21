package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;

public class CerrarSesion implements Task {
    public static CerrarSesion actual() {
        return new CerrarSesion();
    }

    @Override
    @Step("{0} cierra la sesion")
    public <T extends Actor> void performAs(T actor) {
        String access = actor.recall("accessToken");
        String refresh = actor.recall("refreshToken");

        actor.attemptsTo(
                Post.to("/api/auth/logout")
                        .with(req -> req.header("Authorization", "Bearer " + access)
                                .contentType(ContentType.JSON)
                                .body(Map.of("refreshToken", refresh))));
    }
}
