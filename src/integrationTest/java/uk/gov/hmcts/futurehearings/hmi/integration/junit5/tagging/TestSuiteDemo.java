package uk.gov.hmcts.futurehearings.hmi.integration.junit5.tagging;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses(uk.gov.hmcts.futurehearings.hmi.integration.junit5.tagging.TaggingTest.class)
@IncludeTags("Prod")
public class TestSuiteDemo {
}

