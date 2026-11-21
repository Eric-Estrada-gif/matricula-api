package pe.edu.cibertec.matricula_api.screenplay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

public class Historial {
    private Historial() {
    }

    public static Question<Integer> creditosAprobados() {
        return new Question<>() {
            @Override
            public Integer answeredBy(Actor actor) {
                return actor.asksFor(LastResponse.received())
                        .jsonPath().getInt("creditosAprobados");
            }
        };
    }

    public static Question<Double> promedioPonderado() {
        return new Question<>() {
            @Override
            public Double answeredBy(Actor actor) {
                return actor.asksFor(LastResponse.received())
                        .jsonPath().getDouble("promedioPonderado");
            }
        };
    }
}
