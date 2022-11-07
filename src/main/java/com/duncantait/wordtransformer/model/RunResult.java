
package com.duncantait.wordtransformer.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RunResult {
    private boolean isSuccess;
    private List<String> path;

    private RunContext runContext;
}
