package com.github.elenaAeternanox.seventhHomeWork;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckFilesTest {

    @Test
    @DisplayName("Check zip file")
    void checkZipFile() throws IOException {
        String fileName = "test.txt";

        ClassLoader classLoader = this.getClass().getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("test.zip");
             ZipInputStream zip = new ZipInputStream(is)) {
            ZipEntry zipEntry;
            while ((zipEntry = zip.getNextEntry()) != null) {
                assertTrue(zipEntry.getName().contains(fileName));
            }
        }
    }

    @Test
    @DisplayName("Download and check pdf file")
    void downloadAndCheckPdfFile() throws IOException {
        String textFromFile = "При включении";

        open("https://laukar.com/goods/527/112133/instruction");

        File pdfFile = $(byText("Скачать инструкцию")).download();
        PDF pdfContent = new PDF(pdfFile);

        assertEquals(16, pdfContent.numberOfPages);
        assertTrue(pdfContent.text.contains(textFromFile));
    }

    @Test
    @DisplayName("Download and check excel file")
    void downloadAndCheckXlsFile() throws FileNotFoundException {
        String sheetName = "CKMT";
        String rowElectrodes = "ЭЛЕКТРОДЫ";
        double minPriceLastRow = 180.0;

        open("https://ckmt.ru/price-download.html");

        File xlsFile = $(byText("Скачать")).parent().download();
        XLS xls = new XLS(xlsFile);

        assertEquals(sheetName, xls.excel.getSheetName(0));
        assertEquals(rowElectrodes, xls.excel.getSheet(sheetName).getRow(6).getCell(0).getStringCellValue());
        assertEquals(minPriceLastRow, xls.excel.getSheet(sheetName).getRow(491).getCell(5).getNumericCellValue());
    }
}
