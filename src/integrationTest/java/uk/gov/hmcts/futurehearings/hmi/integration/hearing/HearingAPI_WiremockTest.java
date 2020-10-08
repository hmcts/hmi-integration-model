package uk.gov.hmcts.futurehearings.hmi.integration.hearing;

import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.Assert.assertEquals;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.integration.hearing.steps.HearingSteps;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@RunWith(SpringIntegrationSerenityRunner.class)
@Narrative(text={"In order to test that the Hearing Service is working properly",
        "As a tester",
        "I want to be able to execute the tests for various endpoints"})
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("integration")
//@ExtendWith(HoverflyExtension.class)
public class   HearingAPI_WiremockTest {

    @Steps
    HearingSteps hearingSteps;

    @Value("${employeeBaseURI}")
    private String employeeBaseURI;

    @Value("${employeeAPIPath}")
    private String employeeAPIPath;

    @Value("${timeAPIPath}")
    private String timeAPIPath;

    @Value("${hmctsAPIPath}")
    private String hmctsAPIPath;

    @Value("${serenity.outputDirectory}")
    private String serenityOutputDirectory;

    private static final String INPUT_FILE_PATH = "uk/gov/hmcts/futurehearings/hmi/integration/hearing/input";

    /*@Rule
    public WireMockRule wireMockRule = new WireMockRule(options().dynamicPort());*/

   /* @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(9000);*/

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(8080);

    @Rule
    public WireMockClassRule instanceRule = wireMockRule;


    @Before
    public void initialiseValues() {

       /* headersAsMap.put("Host", targetHost);
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Company-Name", "HMCTS");*/

        RestAssured.baseURI = employeeBaseURI;
        SerenityRest.useRelaxedHTTPSValidation();
    }


    @Test
    public void should_get_time_on_employee_from_mock() {
        hearingSteps.invokeEmployeeWithPathParam(employeeAPIPath,
                                    "/product/p0001",
                    "/mock-demo-request.json");
    }

    @Ignore
    @Test
    public void should_Email_on_employee_from_mock() {

        hearingSteps.invokeEmployeeWithQueryParam(employeeAPIPath,
                                                    "/product/p0001",
                                    "/mock-demo-request.json");
    }
}
