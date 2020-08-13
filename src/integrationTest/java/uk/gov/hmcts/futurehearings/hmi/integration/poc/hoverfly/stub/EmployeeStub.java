package uk.gov.hmcts.futurehearings.hmi.integration.poc.hoverfly.stub;

import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static uk.gov.hmcts.futurehearings.hmi.integration.common.TestingUtils.readFileContents;

import java.io.IOException;

import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit.dsl.HoverflyDsl;

public class EmployeeStub {

    public static final void configureHearingStubForGet(final Hoverfly hoverfly,
                                                         String baseURI,
                                                         String path,
                                                         String filePath,
                                                         String contentType) {

        // Given
        try {
            hoverfly.simulate(
                    SimulationSource.dsl(
                            HoverflyDsl.service(baseURI)
                                    .get(path)
                                    .willReturn(success(readFileContents(filePath),
                                            contentType))
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
