package com.duncantait.wordtransformer.integration;

import com.duncantait.wordtransformer.model.RunContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static com.duncantait.wordtransformer.integration.HappyPath_WordTransformerApplicationIT.TEST_FILENAME;
import static com.duncantait.wordtransformer.integration.helper.WordTransformerApplicationITHelper.createFileFrom;
import static com.duncantait.wordtransformer.integration.helper.WordTransformerApplicationITHelper.deleteFile;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(args = TEST_FILENAME)
@ExtendWith(OutputCaptureExtension.class)
class HappyPath_WordTransformerApplicationIT {

    public static final String TEST_FILENAME = "test-happy-path-filename.input";

    @BeforeAll
    static void setup() {
        RunContext runContext = RunContext.builder()
                .startWord("cat")
                .endWord("dog")
                .inputValidWords(new String[] {"and", "but", "cat", "cot", "cup", "dot", "dog", "get", "his", "not", "you"})
                .build();

        createFileFrom(runContext, TEST_FILENAME);
    }

    @AfterAll
    static void tearDown() {
        deleteFile(TEST_FILENAME);
    }

    @Test
    void testHappyPath(CapturedOutput output) {
        RunContext runContext = RunContext.builder()
                .startWord("cat")
                .endWord("dog")
                .inputValidWords(new String[] {"and", "but", "cat", "cot", "cup", "dot", "dog", "get", "his", "not", "you"})
                .build();

        createFileFrom(runContext, TEST_FILENAME);

        assertThat(output).contains("cat, cot, dot, dog");
    }
}
