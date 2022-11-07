
package com.duncantait.wordtransformer.service;

import com.duncantait.wordtransformer.model.RunContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.duncantait.wordtransformer.helper.LogUtil.logErrorAndThrow;

@Component
public class FileReaderService {

    public RunContext readFromFile(String filename) {
        List<String> lines = getLinesFromFile(filename);
        validateNumberOfLines(lines);

        String[] firstLineSplit = lines.get(0).split(", ");
        validateFirstLine(firstLineSplit);
        String startWord = firstLineSplit[0];
        String endWord = firstLineSplit[1];
        String[] secondLineSplit = lines.get(1).split(", ");
        validateSecondLine(secondLineSplit);

        return RunContext.builder()
                .startWord(startWord)
                .endWord(endWord)
                .inputValidWords(secondLineSplit)
                .build();
    }

    private static void validateNumberOfLines(List<String> lines) {
        if (lines.size() != 2) {
            logErrorAndThrow("ERROR: Expected 2 lines in file. Found %s lines".formatted(lines.size()));
        }
    }

    private static void validateFirstLine(String[] firstLineSplit) {
        if (firstLineSplit.length != 2) {
            logErrorAndThrow("ERROR: Expected 2 elements on first line delimited by \",\" . Found %s elements".formatted(firstLineSplit.length));
        }
    }

    private static void validateSecondLine(String[] secondLineSplit) {
        if (secondLineSplit.length == 0) {
            logErrorAndThrow("ERROR: Expected at least 1 element on second line, delimited by \",\" . Found 0 elements");
        }
    }

    private static List<String> getLinesFromFile(String filename) {
        try {
            Path path = Paths.get(System.getProperty("user.dir") + File.separator + filename);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (NoSuchFileException fileNotFoundException) {
            logErrorAndThrow("ERROR: File not found: %s".formatted(filename));
        } catch (IOException ioException) {
            logErrorAndThrow("ERROR: IOException: %s".formatted(ioException.getMessage()));
        } catch (Exception e) {
            logErrorAndThrow("ERROR: Unhandled exception: %s".formatted(e.getMessage()));
        }
        throw new RuntimeException("Unreachable");
    }


}
