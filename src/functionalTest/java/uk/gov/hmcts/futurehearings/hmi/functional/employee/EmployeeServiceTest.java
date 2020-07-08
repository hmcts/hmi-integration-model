package uk.gov.hmcts.futurehearings.hmi.functional.employee;

import uk.gov.hmcts.futurehearings.hmi.functional.employee.steps.EmployeeSteps;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.FileReader.readFileContents;


import java.io.IOException;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.test.context.ActiveProfiles;

@RunWith(SerenityRunner.class)
@Narrative(text={"In order to test that the Employee Service is working properly",
        "As a tester",
        "I want to be able to execute the tests for various endpoints"})
@SpringBootTest
@ActiveProfiles("functional")
public class EmployeeServiceTest {

    @Steps
    EmployeeSteps cftOrCrime;

    @Value("${targetInstance}") private String targetInstance;


    @Test
    public void testSuccessfullPost () throws IOException  {
       /* String input =
                readFileContents("uk/gov/hmcts/futurehearings/hmi/functional/poc-input.json");
        System.out.println("The value of the Input File : "+ input);*/
        System.out.println("The target instance of the Functional Test : " + targetInstance);
        cftOrCrime.invokeEmployee();
        /*String responseString = expect().that().statusCode(200)
                .given().contentType("application/json")
                .baseUri("http://localhost:8080")
                .basePath("employees")
                .when().get().then().extract().asString();*/
        //System.out.println("The value of the response String "+responseString);
    }
}
