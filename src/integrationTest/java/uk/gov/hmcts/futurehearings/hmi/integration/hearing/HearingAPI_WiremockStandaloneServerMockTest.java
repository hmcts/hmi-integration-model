package uk.gov.hmcts.futurehearings.hmi.integration.hearing;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static net.serenitybdd.rest.SerenityRest.expect;
import static org.junit.Assert.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.integration.common.TestingUtils.readFileContents;

import uk.gov.hmcts.futurehearings.hmi.Application;

import java.io.IOException;
import java.util.Map;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Narrative;
import org.junit.Before;
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
public class HearingAPI_WiremockStandaloneServerMockTest {

    private static final String INPUT_FILE_PATH = "uk/gov/hmcts/futurehearings/hmi/integration/hearing/input";

    WireMock wireMock = new WireMock("localhost", 8080);
    //WireMockServer wireMockServer = new WireMockServer("",8080);

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

    @Before
    public void initialiseValues() {
        RestAssured.baseURI = employeeBaseURI;
        SerenityRest.useRelaxedHTTPSValidation();
    }

    @Test
    public void should_work_from_standalone_mock() throws Exception {

        wireMock.resetRequests();

        System.out.println("The value of the base URI"+RestAssured.baseURI );
        //wireMock.start();
        try {
            wireMock.stubFor(get(urlEqualTo("/product/p0001"))
                    //.withHeader("Content-Type", equalTo(contentType))
                    .willReturn(aResponse()
                            .withStatus(200)
                            //.withHeader("Content-Type", contentType)
                            .withBody(readFileContents(INPUT_FILE_PATH+"/mock-demo-request.json"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Response response = expect().that().statusCode(200)
                .given().contentType("application/json")
                //.body(readFileContents(INPUT_FILE_PATH + "/employee-demo-request.json"))
                //.headers(headersAsMap)
                //.baseUri("http://localhost:4550")
                .basePath("/employee")
                .queryParam("id","3")
                .when().get().then().extract().response();


        //wireMock.verify(1, getRequestedFor(urlEqualTo("/product/p0001")));
        //wireMock.verify(1, getRequestedFor(urlEqualTo("/product/p0001")));

        // When
        //LocalTime time = response.getTime("cet");
        System.out.println(response.getBody().asString());
        assertEquals(4,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("Tuesday",responseMap.get(("firstName")));

    }

   /* @After
    public void reset() {
        WireMock.reset();
    }*/
}
