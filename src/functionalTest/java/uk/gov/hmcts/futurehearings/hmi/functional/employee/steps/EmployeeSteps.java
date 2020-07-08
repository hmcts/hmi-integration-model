package uk.gov.hmcts.futurehearings.hmi.functional.employee.steps;

import static net.serenitybdd.rest.SerenityRest.expect;
import static org.junit.Assert.assertEquals;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.springframework.beans.factory.annotation.Value;

public class EmployeeSteps {

    private String actor;

    @Step("#actor routes to {0} in order to get invoke {1}")
    public void invokeEmployee (final String basePath,
                                final String api) {

        System.out.println("The value of the base path " + basePath);
        Response response = expect().that().statusCode(200)
                .given().contentType("application/json")
                .baseUri(basePath)
                .basePath(api)
                .when().get().then().extract().response();
        assertEquals(3,response.jsonPath().getList("$").size());
    }
}
