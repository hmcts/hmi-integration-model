package uk.gov.hmcts.futurehearings.hmi.integration.junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import uk.gov.hmcts.futurehearings.hmi.Application;
import uk.gov.hmcts.futurehearings.hmi.integration.junit5.delegate.HearingTestDelegate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.microsoft.applicationinsights.core.dependencies.io.grpc.netty.shaded.io.netty.util.internal.MathUtil;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.junit.spring.integration.SpringIntegrationSerenityRunner;
import net.thucydides.core.annotations.Narrative;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestInfo;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = {Application.class})
@ActiveProfiles("integration")
public class TrialTestContainer {

    @Value("${employeeBaseURI}")
    private String employeeBaseURI;

    @Value("${targetInstance}")
    private String targetInstance;

    @Value("${targetSubscriptionKey}")
    private String targetSubscriptionKey;

    @Value("${employeeAPIPath}")
    private String employeeAPIPath;

    @Value("${timeAPIPath}")
    private String timeAPIPath;

    @Value("${hmctsAPIPath}")
    private String hmctsAPIPath;

    @Value("${serenity.outputDirectory}")
    private String serenityOutputDirectory;

    @Autowired
    private HearingTestDelegate hearingDelegate;

    @BeforeEach
    public void beforeEach(TestInfo info) {
        System.out.println("Before execute "+info.getTestMethod().get().getName());
        System.out.println("The value of the targetInstance "+targetInstance);
    }
   /* @TestFactory
    Collection<DynamicTest> dynamicTestsFromCollection() {
        return Arrays.asList(
                dynamicTest("1st dynamic test"));
    }*/

    @AfterEach
    public void afterEach(TestInfo info) {
        System.out.println("After execute "+info.getTestMethod().get().getName());
    }

    // Static test 1
    @Test
    public void test_Add() {
        assertEquals(5, 5);
    }

    // This method produces Dynamic test cases
    @TestFactory
    @DisplayName("Tests to Test the basic Invocations")
    public Collection<DynamicTest> dynamicTestsFromCollection() {

        return Arrays.asList(
                dynamicTest("1st dynamic test", () -> {
                    //assertTrue(true);
                    //hearingDelegate.test_successfull_post(targetSubscriptionKey,
                       //     targetInstance);
                }),
                dynamicTest("2nd dynamic test", () -> assertEquals(5, 5)));

    }

    // Static test 2
    @Test
    public void test_Devide() {
        assertEquals(5, 5);
    }

    @Nested
    @DisplayName("Tests for the method A")
    class A {

        @BeforeEach
        void beforeEach() {
            System.out.println("Before each test method of the A class");
        }

        @AfterEach
        void afterEach() {
            System.out.println("After each test method of the A class");
        }

        @Test
        @DisplayName("Example test for method A")
        void sampleTestForMethodA() {
            System.out.println("Example test for method A");
        }
    }
}
