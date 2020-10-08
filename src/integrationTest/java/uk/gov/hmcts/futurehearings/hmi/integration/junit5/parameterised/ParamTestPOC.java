package uk.gov.hmcts.futurehearings.hmi.integration.junit5.parameterised;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ParamTestPOC {

    @DisplayName("Test Test")
    @ParameterizedTest(name = "\"{0}\" should be {1}")
    @ValueSource(strings = { "Hello", "JUnit" })
    void withValueSource(String word) {
        System.out.println("The value of word :"+word);
        assertNotNull(word);
    }
}
