package uk.gov.hmcts.futurehearings.hmi.functional.employee.steps;

import static net.serenitybdd.rest.SerenityRest.expect;
import static org.junit.Assert.assertEquals;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.springframework.beans.factory.annotation.Value;

public class EmployeeSteps {

    private String actor;

    @Value("${targetInstance}") private String targetInstance;

    @Step
    public void invokeEmployee () {

       /*String responseString = expect().that().statusCode(200)
                .given().contentType("application/json")
                .baseUri("http://localhost:4550")
                .basePath("employees")
                .when().get().then().extract().asString();*/
        Response response = expect().that().statusCode(200)
                .given().contentType("application/json")
                .baseUri("http://localhost:4550")
                .basePath("employees")
                .when().get().then().extract().response();
        assertEquals(3,response.jsonPath().getList("$").size());
    }
}
