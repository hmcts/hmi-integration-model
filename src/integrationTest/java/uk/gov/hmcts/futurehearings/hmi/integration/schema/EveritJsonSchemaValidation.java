package uk.gov.hmcts.futurehearings.hmi.integration.schema;

import static uk.gov.hmcts.futurehearings.hmi.integration.common.TestingUtils.readFileContents;

import java.io.IOException;
import java.net.URI;

import org.codehaus.stax2.validation.Validatable;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.Validator;
import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.regexp.RE2JRegexpFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;

public class EveritJsonSchemaValidation {

    private static final String INPUT_FILE_PATH = "uk/gov/hmcts/futurehearings/hmi/integration/hearing/input";

    @Test
    public void TestBasicSchemaValidation () throws Exception {

        JSONObject jsonSchema = new JSONObject(
                new JSONTokener(readFileContents(INPUT_FILE_PATH+"/schema.json")));
        JSONObject jsonSubject = new JSONObject(
                new JSONTokener(readFileContents(INPUT_FILE_PATH+"/product_invalid.json")));

        Schema schema = SchemaLoader.load(jsonSchema);
        schema.validate(jsonSubject);
    }

    @Test
    public void TestInternalRefSchemaValidation () throws IOException, JSONException {

        JSONObject jsonSchema = new JSONObject(
                new JSONTokener(readFileContents(INPUT_FILE_PATH+"/internal-ref-schema.json")));
        JSONObject jsonSubject = new JSONObject(
                new JSONTokener(readFileContents(INPUT_FILE_PATH+"/rectangle-payload-single-failure.json")));

        Schema schema = SchemaLoader.load(jsonSchema);
        try {
            schema.validate(jsonSubject);
        }  catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void TestInternalRefSchemaValidationMultipleFailures () throws IOException, JSONException {

        JSONObject jsonSchema = new JSONObject(
                new JSONTokener(readFileContents(INPUT_FILE_PATH+"/internal-ref-schema.json")));
        JSONObject jsonSubject = new JSONObject(
                new JSONTokener(readFileContents(INPUT_FILE_PATH+"/rectangle-payload-multiple-failures.json")));

        Schema schema = SchemaLoader.load(jsonSchema);
        try {
            schema.validate(jsonSubject);
        }  catch (ValidationException e) {
            System.out.println(e.getMessage());
            e.getCausingExceptions().stream()
                    .map(ValidationException::getMessage)
                    .forEach(System.out::println);
        }
    }

    @Test
    public void testValidator () throws IOException, JSONException {

        JSONObject jsonSchema = new JSONObject(
                new JSONTokener(readFileContents(INPUT_FILE_PATH+"/internal-ref-schema.json")));
        JSONObject jsonSubject = new JSONObject(
                new JSONTokener(readFileContents(INPUT_FILE_PATH+"/rectangle-payload-multiple-failures.json")));

        Schema schema = SchemaLoader.load(jsonSchema);

        Validator validator = Validator.builder()
                //.failEarly()
                .build();
        try {
            validator.performValidation(schema, jsonSubject);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            e.getCausingExceptions().stream()
                    .map(ValidationException::getMessage)
                    .forEach(System.out::println);
        }
    }

    @Test
    public void TestBasicSchemaValidationWithDefaultsValueTaken () throws Exception {

        JSONObject jsonSchema = new JSONObject(
                new JSONTokener(readFileContents(INPUT_FILE_PATH+"/internal-ref-schema.json")));
        JSONObject jsonSubject = new JSONObject(
                new JSONTokener(readFileContents(INPUT_FILE_PATH+"/rectangle-payload-multiple-failures.json")));

        Schema schema = SchemaLoader.builder()
                .useDefaults(true)
                .regexpFactory(new RE2JRegexpFactory())
                .schemaJson(jsonSchema)
                .build()
                .load().build();
        try {
            schema.validate(jsonSubject);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            e.getCausingExceptions().stream()
                    .map(ValidationException::getMessage)
                    .forEach(System.out::println);
        }
    }

    @Test
    public void TestBasicSchemaValidationWithExternalReferences () throws Exception {


        JSONObject jsonPayload = new JSONObject(
                new JSONTokener(readFileContents(INPUT_FILE_PATH+"/request-hearing-mandatory-payload.json")));

        JSONObject jsonSchemaReusable = new JSONObject(
                new JSONTokener(readFileContents(INPUT_FILE_PATH+"/reusable.json")));
        JSONObject jsonSchema = new JSONObject(
                new JSONTokener(readFileContents(INPUT_FILE_PATH+"/postHearingRequestMessage.json")));

        Schema schema = SchemaLoader.builder()
                .useDefaults(true)
                .regexpFactory(new RE2JRegexpFactory())
                .registerSchemaByURI(new URI("http://www.gov.uk/reusable.json"), jsonSchemaReusable)
                .schemaJson(jsonSchema)
                .resolutionScope("classpath://uk/gov/hmcts/futurehearings/hmi/integration/hearing/input/")
                .build()
                .load().build();

        try {
            schema.validate(jsonPayload);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            e.getCausingExceptions().stream()
                    .map(ValidationException::getMessage)
                    .forEach(System.out::println);
        }
    }
}
