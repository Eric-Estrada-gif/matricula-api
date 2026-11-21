package pe.edu.cibertec.matricula_api.screenplay.task;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class ConsultarHistorial implements Task {
    private final String alumno;

    public ConsultarHistorial(String alumno) {
        this.alumno = alumno;
    }

    public static ConsultarHistorial deAlumno(String alumno) {
        return new ConsultarHistorial(alumno);
    }

    @Override
    @Step("{0} consulta el historial del alumno #alumno")
    public <T extends Actor> void performAs(T actor) {
        String access = actor.recall("accessToken");
        actor.attemptsTo(
                Get.resource("/api/alumnos/" + alumno + "/historial")
                        .with(req -> req.header("Authorization", "Bearer " + access)));
    }
}
