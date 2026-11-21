package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

import java.util.Map;

public class IniciarSesion implements Task {

    private final String usuario;
    private final String clave;

    public IniciarSesion(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    public static IniciarSesion conCredenciales(String usuario, String clave) {
        return new IniciarSesion(usuario, clave);
    }

    @Override
    @Step("{0} inicia sesion con el usuario #usuario")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/api/auth/login")
                        .with(req -> req.contentType(ContentType.JSON)
                                .body(Map.of("usuario", usuario, "clave", clave))));

        Response respuesta = actor.asksFor(LastResponse.received());
        if (respuesta.statusCode() == 200) {
            String access = respuesta.jsonPath().getString("accessToken");
            String refresh = respuesta.jsonPath().getString("refreshToken");
            actor.remember("accessToken", access);
            actor.remember("refreshToken", refresh);
            actor.remember("refreshOriginal", refresh);
        }
    }
}
