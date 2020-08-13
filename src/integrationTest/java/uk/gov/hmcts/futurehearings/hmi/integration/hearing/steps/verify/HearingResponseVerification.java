package uk.gov.hmcts.futurehearings.hmi.integration.hearing.steps.verify;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.Map;

import io.restassured.response.Response;

public class HearingResponseVerification {

    public static final void verify_should_get_time_on_employee_from_mock_path_param(final Response response,
                                                                                     final String mockAPIPath) {

        verify(1,getRequestedFor(urlEqualTo("/api/json/cet/now")));

        // When
        //LocalTime time = response.getTime("cet");
        System.out.println(response.getBody().asString());
        assertEquals(4,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("Central Europe Standard Time FROM HMCTS",responseMap.get(("lastName")));

    }

    public static final void verify_should_get_time_on_employee_from_mock_query_param(final Response response,
                                                                                      final String mockAPIPath) {

        //verify(1,getRequestedFor(urlPathMatching("/api/json/cet/now")));
        verify(1, getRequestedFor(urlEqualTo("/HMCTSTEST?id=2")));

        // When
        //LocalTime time = response.getTime("cet");
        System.out.println(response.getBody().asString());
        assertEquals(4,response.getBody().jsonPath().getMap("$").size());
        Map<String, String> responseMap = response.getBody().jsonPath().getMap("$");
        assertEquals("Central Europe Standard Time FROM HMCTS",responseMap.get(("email")));

    }
}
