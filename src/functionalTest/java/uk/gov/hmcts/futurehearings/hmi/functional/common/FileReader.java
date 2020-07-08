package uk.gov.hmcts.futurehearings.hmi.functional.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.util.ResourceUtils;

public class FileReader {

    public static String readFileContents (final String path) throws IOException {

        File file = ResourceUtils.getFile("classpath:"+path);
        //File is found
        System.out.println("File Found : " + file.exists());
        return new String(Files.readAllBytes(Paths.get(file.toURI())));
    }
}
