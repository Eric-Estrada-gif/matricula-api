package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Patch;

import java.util.Map;

public class CalificarMatricula implements Task {

    private final double nota;

    public CalificarMatricula(double nota) {
        this.nota = nota;
    }

    public static CalificarMatricula conNota(double nota) {
        return new CalificarMatricula(nota);
    }

    @Override
    @Step("{0} califica la matricula con la nota #nota")
    public <T extends Actor> void performAs(T actor) {
        String access = actor.recall("accessToken");
        String idMatricula = actor.recall("idMatricula");

        actor.attemptsTo(
                Patch.to("/api/matriculas/" + idMatricula + "/nota")
                        .with(req -> req.header("Authorization", "Bearer " + access)
                                .contentType(ContentType.JSON)
                                .body(Map.of("nota", nota))));
    }
}
