package com.duncantait.wordtransformer.service;

import com.duncantait.wordtransformer.model.RunResult;
import org.springframework.stereotype.Component;

@Component
public class PrintOutputService {

    private static final String CANNOT_TRANSFORM_FORMAT_STRING = "Cannot transform %s to %s";

    public void printResult(RunResult runResult) {
        if (!runResult.isSuccess()) {
            System.out.println(CANNOT_TRANSFORM_FORMAT_STRING.formatted(runResult.getRunContext().getStartWord(), runResult.getRunContext().getEndWord()));
        } else {
            String pathString = String.join(", ", runResult.getPath());
            System.out.println(pathString);
        }
    }
}
