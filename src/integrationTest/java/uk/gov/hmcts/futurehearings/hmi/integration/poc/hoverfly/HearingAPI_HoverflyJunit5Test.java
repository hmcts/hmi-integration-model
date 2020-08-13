package uk.gov.hmcts.futurehearings.hmi.integration.poc.hoverfly;

import static net.serenitybdd.rest.SerenityRest.expect;
import static org.junit.Assert.assertEquals;
import static uk.gov.hmcts.futurehearings.hmi.integration.poc.hoverfly.stub.EmployeeStub.configureHearingStubForGet;

import uk.gov.hmcts.futurehearings.hmi.Application;

import java.util.Map;

import io.restassured.response.Response;
import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit5.HoverflyExtension;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.rest.SerenityRest;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("integration")
@ExtendWith(HoverflyExtension.class)
public class HearingAPI_HoverflyJunit5Test {

    //private static final Map<String, Object> headersAsMap = new HashMap<>();

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${serenity.outputDirectory}")
    private String serenityOutputDirectory;

    private static final String INPUT_FILE_PATH = "uk/gov/hmcts/futurehearings/hmi/integration/hearing/input";


    @BeforeClass
    public void initialiseValues() {

       /* headersAsMap.put("Host", targetHost);
        headersAsMap.put("Ocp-Apim-Subscription-Key", targetSubscriptionKey);
        headersAsMap.put("Ocp-Apim-Trace", "true");
        headersAsMap.put("Company-Name", "HMCTS");*/

        //Added a Path here due to no proper Multi Module defined and Restrictions in defining a Serenity.conf definition...
        System.getProperties().put("serenity.outputDirectory" , serenityOutputDirectory);

        //RestAssured.baseURI = targetInstance;
        SerenityRest.useRelaxedHTTPSValidation();
    }

    @Test
    public void should_get_time_from_external_service(Hoverfly hoverfly) {

        configureHearingStubForGet(hoverfly,
                                "http://worldclockapi.com",
                                "/api/json/cet/now",
                                INPUT_FILE_PATH + "/mock-demo-request.json",
                                "application/json");


        final Response response = expect().that().statusCode(200)
                .given().contentType("application/json")
                //.body(payloadBody)
                //.headers(headersAsMap)
                .baseUri("http://worldclockapi.com")
                .basePath("/api/json/cet/now")
                .when().get().then().extract().response();

        // When
        //LocalTime time = response.getTime("cet");
        System.out.println(response.getBody().asString());
        assertEquals(9,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("Central Europe Standard Time FROM HMCTS",responseMap.get(("timeZoneName")));

        // Then
        /*Assertions.assertThat(time.getHour()).isEqualTo(10);
        Assertions.assertThat(time.getMinute()).isEqualTo(54);
        */
    }

    @Test
    public void should_get_time_on_employee_from_mock(Hoverfly hoverfly) {

        configureHearingStubForGet(hoverfly,
                "http://worldclockapi.com",
                "/api/json/cet/now",
                INPUT_FILE_PATH + "/mock-demo-request.json",
                "application/json");


        final Response response = expect().that().statusCode(201)
                .given().contentType("application/json")
                //.body(payloadBody)
                //.headers(headersAsMap)
                .baseUri("http://localhost:4550")
                .basePath("/employee/1")
                .when().post().then().extract().response();

        // When
        //LocalTime time = response.getTime("cet");
        System.out.println(response.getBody().asString());
        assertEquals(4,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("Central Europe Standard Time FROM HMCTS",responseMap.get(("lastName")));

        // Then
        /*Assertions.assertThat(time.getHour()).isEqualTo(10);
        Assertions.assertThat(time.getMinute()).isEqualTo(54);
        */
    }
}
