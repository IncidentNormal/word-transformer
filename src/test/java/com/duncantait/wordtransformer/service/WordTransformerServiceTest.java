package com.duncantait.wordtransformer.service;

import com.duncantait.wordtransformer.model.RunContext;
import com.duncantait.wordtransformer.model.RunResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WordTransformerServiceTest {

    private WordTransformerService underTest;

    @BeforeEach
    void setup() {
        underTest = new WordTransformerService();
    }

    @Test
    void testSampleInput1() {
        RunContext runContext = RunContext.builder()
                .startWord("cat")
                .endWord("dog")
                .inputValidWords(new String[] {"and", "but", "cat", "cot", "cup", "dot", "dog", "get", "his", "not", "you"})
                .build();

        RunResult transformPath = underTest.getTransformPath(runContext);

        assertThat(transformPath.isSuccess()).isTrue();
        assertThat(transformPath.getPath()).isEqualTo(List.of("cat", "cot", "dot", "dog"));
    }

    @Test
    void testSampleInput2() {
        RunContext runContext = RunContext.builder()
                .startWord("cat")
                .endWord("pig")
                .inputValidWords(new String[] {"and", "but", "cat", "cot", "cup", "dot", "dog", "get", "his", "not", "you"})
                .build();

        RunResult transformPath = underTest.getTransformPath(runContext);

        assertThat(transformPath.isSuccess()).isFalse();
        assertThat(transformPath.getPath()).isEmpty();
    }

}