package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.List;
import java.util.Map;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CotizarCurso implements Task {
    private final String curso;
    private final String alumno;

    public CotizarCurso(String curso, String alumno) {
        this.curso = curso;
        this.alumno = alumno;
    }

    public static CotizarCurso deCurso(String curso, String alumno) {
        return instrumented(CotizarCurso.class, curso, alumno);
    }

    @Override
    @Step("{0} cotiza el curso #curso")
    public <T extends Actor> void performAs(T actor) {
        String access = actor.recall("accessToken");
        actor.attemptsTo(
                Post.to("/api/matriculas/cotizacion")
                        .with(req -> req.header("Authorization", "Bearer " + access)
                                .contentType(ContentType.JSON)
                                .body(Map.of("codigoAlumno", alumno, "codigosCursos", List.of(curso)))));
    }
}
