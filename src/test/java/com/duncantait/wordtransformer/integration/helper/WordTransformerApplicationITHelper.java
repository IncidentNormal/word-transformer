
package com.duncantait.wordtransformer.integration.helper;

import com.duncantait.wordtransformer.model.RunContext;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@UtilityClass
public class WordTransformerApplicationITHelper {

    public static void createFileFrom(RunContext runContext, String filename) {
        Path path = tempFilePath(filename);
        try {
            deleteFile(filename);
            Path tempFile = Files.createFile(path);

            String firstLine = "%s, %s".formatted(runContext.getStartWord(), runContext.getEndWord());
            String secondLine = String.join(", ", runContext.getInputValidWords());
            List<String> content = List.of(firstLine, secondLine);
            Files.write(tempFile, content, StandardOpenOption.CREATE);

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void deleteFile(String filename) {
        Path path = tempFilePath(filename);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static Path tempFilePath(String filename) {
        return Paths.get(System.getProperty("user.dir") + File.separator + filename);
    }

}
