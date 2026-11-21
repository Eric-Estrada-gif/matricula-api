package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import java.util.Map;

public class RenovarToken implements Task {
    private final String claveMemoria;

    public RenovarToken(String claveMemoria) {
        this.claveMemoria = claveMemoria;
    }

    public static RenovarToken actual() {
        return new RenovarToken("refreshToken");
    }

    public static RenovarToken original() {
        return new RenovarToken("refreshOriginal");
    }

    @Override
    @Step("{0} renueva el token")
    public <T extends Actor> void performAs(T actor) {
        String refresh = actor.recall(claveMemoria);

        actor.attemptsTo(
                Post.to("/api/auth/refresh")
                        .with(req -> req.contentType(ContentType.JSON)
                                .body(Map.of("refreshToken", refresh))));

        Response respuesta = actor.asksFor(LastResponse.received());
        if (respuesta.statusCode() == 200) {
            actor.remember("accessToken", respuesta.jsonPath().getString("accessToken"));
            actor.remember("refreshToken", respuesta.jsonPath().getString("refreshToken"));
        }
    }
}
