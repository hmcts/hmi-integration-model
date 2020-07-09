package uk.gov.hmcts.futurehearings.hmi.functional.hearing;

import uk.gov.hmcts.futurehearings.hmi.functional.hearing.steps.HearingSteps;
import uk.gov.hmcts.futurehearings.hmi.functional.Application;
import static uk.gov.hmcts.futurehearings.hmi.functional.common.FileReader.readFileContents;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text={"In order to test that the Employee Service is working properly",
        "As a tester",
        "I want to be able to execute the tests for various endpoints"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("functional")
public class EmployeeServiceTest {

    @Steps
    HearingSteps hearingSteps;

    @Value("${targetInstance}")
    private String targetInstance;

    private static final String EMPLOYEE_API = "employees";
    private static final String SESSION_API = "future-hearings-api/session/1?time=morning";
    private static final String HEARINGS_API = "future-hearings-api/hearings";
    Map<String, Object> headersAsMap = new HashMap<>();

    @Before
    public void initialiseValues() {
        headersAsMap.put("Host", "fh-hmi-apim-svc.azure-api.net");
        headersAsMap.put("Ocp-Apim-Subscription-Key", "2092a4fe20234db59a30deda06e37b72");
        headersAsMap.put("Ocp-Apim-Trace", "true");

        //SerenityRest = targetInstance;
        SerenityRest.useRelaxedHTTPSValidation();
    }


    /*@Test
    @Ignore
    public void testSuccessfullGet() throws IOException {
        String input =
                readFileContents("uk/gov/hmcts/futurehearings/hmi/functional/input/poc-input.json");
        *//*System.out.println("The value of the Input File : "+ input);
        System.out.println("The target instance of the Functional Test : " + targetInstance);*//*
        cftOrCrime.invokeEmployee(targetInstance,
                SESSION_API,
                headersAsMap);
    }*/

    @Test
    public void testSuccessfullPostToHearing() throws IOException {

        String input =
                readFileContents("uk/gov/hmcts/futurehearings/hmi/functional/input/mock-demo-request.json");
        hearingSteps.requestHearing(targetInstance,
                                    HEARINGS_API,
                                    headersAsMap,
                                    input);
    }
}