package pe.edu.cibertec.matricula_api.screenplay.task;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

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
    public <T extends Actor> void performAs (T actor) {
        actor.attemptsTo(
                Post.to("api/matriculas/cotizar")
                        .with(req -> req.contentType(ContentType.JSON)
                                .body(Map.of("codigoAlumno", alumno, "codigoCurso", curso)))
        );
    }
}
