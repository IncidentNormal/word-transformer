package com.duncantait.wordtransformer;

import com.duncantait.wordtransformer.model.RunContext;
import com.duncantait.wordtransformer.model.RunResult;
import com.duncantait.wordtransformer.service.FileReaderService;
import com.duncantait.wordtransformer.service.PrintOutputService;
import com.duncantait.wordtransformer.service.WordTransformerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.duncantait.wordtransformer.helper.LogUtil.logErrorAndThrow;

@SpringBootApplication
@RequiredArgsConstructor
public class WordTransformerApplication implements CommandLineRunner {

    private final FileReaderService fileReaderService;
    private final WordTransformerService wordTransformerService;
    private final PrintOutputService printOutputService;

    public static void main(String[] args) {
        SpringApplication.run(WordTransformerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        validateArgs(args);
        String filename = getFilename(args);
        RunContext runContext = fileReaderService.readFromFile(filename);
        RunResult runResult = wordTransformerService.getTransformPath(runContext);
        printOutputService.printResult(runResult);
    }

    private static void validateArgs(String[] args) {
        if (args.length != 1 && args.length != 2) {
            logErrorAndThrow("ERROR: Expected 1 or 2 arguments ([0]=filename, or [0]=main class & [1]=filename). Found %s arguments".formatted(args.length));
        }
    }

    private static String getFilename(String[] args) {
        if (args.length == 1) {
            return args[0];
        }
        if (args.length == 2) {
            return args[1];
        }
        throw new IllegalStateException("invalid number of args: %s, expecting 1 or 2".formatted(args.length));
    }
}
