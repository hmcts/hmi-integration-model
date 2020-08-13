package uk.gov.hmcts.futurehearings.hmi.integration.hearing.stub;


import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static uk.gov.hmcts.futurehearings.hmi.integration.common.TestingUtils.readFileContents;

import java.io.IOException;

public class WiremockStub {

    public static final void configureHearingStubForGet(String path,
                                                        String filePath,
                                                        String contentType) {
        try {
            stubFor(get(urlEqualTo(path))
                    //.withHeader("Content-Type", equalTo(contentType))
                    .willReturn(aResponse()
                            .withStatus(200)
                            //.withHeader("Content-Type", contentType)
                            .withBody(readFileContents(filePath))));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static final void configureHearingStubForGetWithParams(String path,
                                                        String filePath) {
        try {
            stubFor(get(urlEqualTo("/HMCTSTEST?id=2"))
                    //.withHeader("Content-Type", equalTo(contentType))
                    //.withQueryParams("id","2")
                    .willReturn(aResponse()
                            .withStatus(202)
                            //.withHeader("Content-Type", contentType)
                            .withBody(readFileContents(filePath))));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
