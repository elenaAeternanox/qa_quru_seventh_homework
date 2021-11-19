package com.github.elenaAeternanox.seventhHomeWork;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class CheckZipFileTest {

    @Test
    @DisplayName("Check txt file in zip")
    void checkTxtFileInZip() throws IOException {
        String fileName = "test.txt";
        String fileTitle = "Txt file";
        String fileText = "File for the check txt file in zip";

        ClassLoader classLoader = this.getClass().getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("test.zip");
             ZipInputStream zip = new ZipInputStream(is)) {
            ZipEntry zipEntry;
            while ((zipEntry = zip.getNextEntry()) != null) {
                assertTrue(zipEntry.getName().contains(fileName));
                if (zipEntry.getName() == fileName) {
                    FileOutputStream fos = new FileOutputStream("src/test/resources/" + zipEntry.getName());
                    for (int i = zip.read(); i != -1; i = zip.read()) {
                        fos.write(i);
                    }
                    fos.flush();
                    zip.closeEntry();
                    fos.close();

                    File txt = new File("src/test/resources/" + fileName);
                    String txtContent = IOUtils.toString(new FileReader(txt));
                    assertTrue(txtContent.contains(fileTitle));
                    assertTrue(txtContent.contains(fileText));
                }
            }
        }
    }
}
