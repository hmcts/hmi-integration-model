package uk.gov.hmcts.futurehearings.hmi.integration.junit5.tagging;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

//@IncludeTags("Prod")
//@SelectPackages(uk.gov.hmcts.futurehearings.hmi.integration.junit5.tagging.class)
@SelectClasses(uk.gov.hmcts.futurehearings.hmi.integration.junit5.tagging.TaggingTest.class)
@IncludeTags("Prod")
public class TaggingTest {

    @Test
    @Tag("Prod")
    public void test_Add() {
        assertEquals(5, 5);
    }

    @Test
    @Tag("Dev")
    public void test_Divide() {
        System.out.println("Inside Divide Dev");
        assertEquals(5, 5);
    }
}

