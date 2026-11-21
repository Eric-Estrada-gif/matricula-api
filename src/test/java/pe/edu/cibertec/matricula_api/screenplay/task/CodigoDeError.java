package pe.edu.cibertec.matricula_api.screenplay.task;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

public class CodigoDeError implements Question<String> {
    public static CodigoDeError enLaRespuesta() {
        return new CodigoDeError();
    }

    @Override
    public String answeredBy(Actor actor) {
        return actor.asksFor(LastResponse.received()).jsonPath().getString("codigo");
    }
}
