package uk.gov.hmcts.futurehearings.hmi.integration.hearing.steps;

import static net.serenitybdd.rest.SerenityRest.expect;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static uk.gov.hmcts.futurehearings.hmi.integration.hearing.steps.verify.HearingResponseVerification.verify_should_get_time_on_employee_from_mock_path_param;
import static uk.gov.hmcts.futurehearings.hmi.integration.hearing.steps.verify.HearingResponseVerification.verify_should_get_time_on_employee_from_mock_query_param;
import static uk.gov.hmcts.futurehearings.hmi.integration.hearing.stub.WiremockStub.configureHearingStubForGet;
import static uk.gov.hmcts.futurehearings.hmi.integration.hearing.stub.WiremockStub.configureHearingStubForGetWithParams;

import net.thucydides.core.annotations.Step;

public class HearingSteps {

    private static final String INPUT_FILE_PATH = "uk/gov/hmcts/futurehearings/hmi/integration/hearing/input";

    private static final String CONTENT_TYPE = "application/json";

    private String actor;

    @Step("#actor invokes Employee API with path {0} expecting a repsonse from {2} from path {1}")
    public void invokeEmployeeWithPathParam (final String baseAPIPath,
                                final String mockAPIPath,
                                final String mockResponseFile) {

        configureHearingStubForGet(
                mockAPIPath,
                INPUT_FILE_PATH + mockResponseFile,
                CONTENT_TYPE);

        //This is an invocation to a API URL with a path param...
        expect().that().statusCode(201)
                .given().contentType(CONTENT_TYPE)
                .basePath(baseAPIPath + "/1")
                .when().post().then().extract().response();

        verify_should_get_time_on_employee_from_mock_path_param(lastResponse(),mockAPIPath);
    }

    @Step("#actor invokes Employee API with path {0}")
    public void invokeEmployeeWithQueryParam (final String baseAPIPath,
                                              final String mockAPIPath,
                                              final String mockResponseFile) {

        configureHearingStubForGetWithParams(
                mockAPIPath,
                INPUT_FILE_PATH + mockResponseFile);

        expect().that().statusCode(200)
                .given().contentType(CONTENT_TYPE)
                //.body(readFileContents(INPUT_FILE_PATH + "/employee-demo-request.json"))
                //.headers(headersAsMap)
                //.baseUri("http://localhost:4550")
                .basePath(baseAPIPath)
                .queryParam("id","2")
                .when().get().then().extract().response();

        verify_should_get_time_on_employee_from_mock_query_param(lastResponse(),mockAPIPath);
    }
}
