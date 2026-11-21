package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import pe.edu.cibertec.matricula_api.screenplay.task.CotizarCurso;
import pe.edu.cibertec.matricula_api.screenplay.task.Matricularse;
import pe.edu.cibertec.matricula_api.screenplay.task.PagarMatricula;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MatriculaStepDefinitions {

    private static final String ALUMNO = "i202610006";

    @When("cotizar el curso {string} ")
            public void cotizarElCurso(String curso) {
        theActorInTheSpotlight().attemptsTo(CotizarCurso.deCurso(curso, ALUMNO));
    }

    @When("se matricula en el curso {string} ")
        public void seMatriculaEnElCurso(String curso) {
            theActorInTheSpotlight().attemptsTo(Matricularse.en(ALUMNO, curso));
    }

        @When("paga la matricula con el metodo {string}")
    public void pagaLaMatricula(String metodo) {
            theActorInTheSpotlight().attemptsTo(PagarMatricula.conMetodo(metodo));
    }

        @And("los creditos son {int}")
    public void losCreditosSon(int creditos) {
        verificarNumero("creditos", creditos);
    }

        @And("el subtotal es  {int}")
    public void elSubtotalEs(int subtotal){
        verificarNumero("subtotal", subtotal);
    }

    @And("el descuento es {int}")
    public void elDescuentoEs(int descuento) {
        verificarNumero("descuento", descuento);
    }

    @And("el total es {int}")
    public void elTotalEs(int total) {
        verificarNumero("total", total);
    }

    @And("el monto de la matrícula es {int}")
    public void elMontoDeLaMatriculaEs(int monto) {
        verificarNumero("monto", monto);
    }

    @And("el estado de la matrícula es {string}")
    public void elEstadoDeLaMatriculaEs(String estado) {
        theActorInTheSpotlight().should(
                seeThatResponse("el estado es " + estado,
                        res -> res.body("estado", equalTo(estado))));
    }

    @And("la fecha de pago tiene valor")
    public void laFechaDePagoTieneValor() {
        theActorInTheSpotlight().should(
                seeThatResponse("la fecha de pago tiene valor",
                        res -> res.body("fechaPago", notNullValue())));
    }

    @And("el pago de la matricula tiene el metodo {string} y un numero de operacion valido")
    public void elPagoTieneMetodoYOperacion(String metodo) {
        theActorInTheSpotlight().should(
                seeThatResponse("el pago usa " + metodo + " con operacion valida",
                        res -> res.body("metodoPago", equalTo(metodo),
                                "numeroOperacion", matchesPattern("OP-\\d{6}"))));
    }

    private void verificarNumero(String campo, int esperado) {
        theActorInTheSpotlight().should(
                seeThatResponse(campo + " es " + esperado,
                        res -> assertThat(
                                res.extract().jsonPath().getDouble(campo),
                                closeTo(esperado, 0.001))));
    }

}
