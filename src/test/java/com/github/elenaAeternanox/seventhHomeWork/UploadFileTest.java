package com.github.elenaAeternanox.seventhHomeWork;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class UploadFileTest {

    @Test
    @DisplayName("Upload file test")
    void uploadFileTest() {
        open("https://demoqa.com/upload-download");

        String uploadFile = "fileForUpload.txt";
        $("input[type='file']").uploadFromClasspath(uploadFile);
        $("#uploadedFilePath").shouldHave(text(uploadFile));

        String secondUploadFile = "secondFileForUpload.txt";
        $("input[type='file']").uploadFromClasspath(secondUploadFile);
        $("#uploadedFilePath").shouldHave(text(secondUploadFile));

    }
}
