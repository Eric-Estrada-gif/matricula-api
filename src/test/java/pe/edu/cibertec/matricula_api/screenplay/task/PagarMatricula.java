package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.Map;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PagarMatricula implements Task {
    private final String metodo;

    public PagarMatricula(String metodo) {
        this.metodo = metodo;
    }

    public static PagarMatricula conMetodo(String metodo) {
        return instrumented(PagarMatricula.class, metodo);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String id = SerenityRest.lastResponse().jsonPath().getString("id");
        actor.attemptsTo(
                Post.to("api/matriculas/{id}/pagar")
                        .with(req -> req.pathParam("id", id)
                                .contentType(ContentType.JSON)
                                .body(Map.of("metodoPago", metodo)))
        );
    }
}
