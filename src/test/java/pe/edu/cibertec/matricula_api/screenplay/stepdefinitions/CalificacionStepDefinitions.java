package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pe.edu.cibertec.matricula_api.screenplay.questions.Historial;
import pe.edu.cibertec.matricula_api.screenplay.task.CalificarMatricula;
import pe.edu.cibertec.matricula_api.screenplay.task.ConsultarHistorial;
import pe.edu.cibertec.matricula_api.screenplay.task.ConsultarMatriculas;
import pe.edu.cibertec.matricula_api.screenplay.task.IniciarSesion;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CalificacionStepDefinitions {
    private static final String CLAVE = "Cibertec2026";

    @Given("que el alumno {string} consulta sus matriculas")
    public void alumnoConsultaSusMatriculas(String alumno) {
        // Si en ComunesStepDefinitions el actor se crea con CallAnApi, copia esa misma configuracion aqui
        theActorCalled("Ana").attemptsTo(
                IniciarSesion.conCredenciales(alumno, CLAVE),
                ConsultarMatriculas.propias());
    }

    @And("la matricula pagada del curso {string} queda registrada")
    public void laMatriculaPagadaQuedaRegistrada(String curso) {
        guardarMatricula(curso, "PAGADA");
    }

    @And("la matricula del curso {string} queda registrada")
    public void laMatriculaQuedaRegistrada(String curso) {
        guardarMatricula(curso, "REGISTRADA");
    }

    @When("el docente {string} califica esa matricula con la nota {double}")
    public void elDocenteCalifica(String docente, double nota) {
        theActorInTheSpotlight().attemptsTo(
                IniciarSesion.conCredenciales(docente, CLAVE),
                CalificarMatricula.conNota(nota));
    }

    @When("el alumno {string} consulta su historial")
    public void elAlumnoConsultaSuHistorial(String alumno) {
        theActorInTheSpotlight().attemptsTo(
                IniciarSesion.conCredenciales(alumno, CLAVE),
                ConsultarHistorial.deAlumno(alumno));
    }

    @And("la condicion de la matricula es {string}")
    public void laCondicionDeLaMatriculaEs(String condicion) {
        theActorInTheSpotlight().should(
                seeThatResponse("la condicion es " + condicion,
                        res -> res.body("condicion", equalTo(condicion))));
    }

    @And("los creditos aprobados son {int}")
    public void losCreditosAprobadosSon(int creditos) {
        theActorInTheSpotlight().should(
                seeThat(Historial.creditosAprobados(), equalTo(creditos)));
    }

    @And("el promedio ponderado es {double}")
    public void elPromedioPonderadoEs(double promedio) {
        theActorInTheSpotlight().should(
                seeThat(Historial.promedioPonderado(), closeTo(promedio, 0.01)));
    }

    private void guardarMatricula(String curso, String estado) {
        var actor = theActorInTheSpotlight();
        String id = net.serenitybdd.rest.SerenityRest.lastResponse().jsonPath()
                .getString("find { it.codigoCurso == '" + curso + "' && it.estado == '" + estado + "' }.id");
        assertThat("No hay matricula " + estado + " del curso " + curso, id, notNullValue());
        actor.remember("idMatricula", id);
    }
}
