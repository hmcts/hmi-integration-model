package uk.gov.hmcts.futurehearings.hmi.functional.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.util.ResourceUtils;

public class FileReader {

    public static String readFileContents (final String path) throws IOException {

        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = ResourceUtils.getFile("classpath:"+path);
        //File file = new File(classLoader.getResource(path).getFile());
        //File is found
        System.out.println("File Found : " + file.exists());

        return new String("");
    }
}
