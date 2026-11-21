package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Patch;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

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
    @Step("{0} paga la matricula con el metodo #metodo")
    public <T extends Actor> void performAs(T actor) {
        String access = actor.recall("accessToken");
        String id = actor.asksFor(LastResponse.received()).jsonPath().getString("id");

        actor.attemptsTo(
                Patch.to("/api/matriculas/" + id + "/pagar")
                        .with(req -> req.header("Authorization", "Bearer " + access)
                                .contentType(ContentType.JSON)
                                .body(Map.of("metodoPago", metodo))));
    }
}
