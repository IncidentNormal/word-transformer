package com.duncantait.wordtransformer.service;

import com.duncantait.wordtransformer.model.RunContext;
import com.duncantait.wordtransformer.model.RunResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class WordTransformerService {

    private static final char[] LOWERCASE_EN_CHARS = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    public RunResult getTransformPath(RunContext runContext) {
        String startWord = runContext.getStartWord();
        String endWord = runContext.getEndWord();
        String[] inputValidWords = runContext.getInputValidWords();

        List<String> transformPath = doGetTransformPath(startWord, endWord, inputValidWords);
        boolean isSuccess = !transformPath.isEmpty();

        return RunResult.builder()
                .isSuccess(isSuccess)
                .path(transformPath)
                .runContext(runContext)
                .build();
    }

    private List<String> doGetTransformPath(String startWord, String endWord, String[] inputValidWords) {
        Map<String, String> wordParentMap = new HashMap<>();
        Set<String> validWords = new HashSet<>(Arrays.asList(inputValidWords));
        traverse(startWord, endWord, validWords, wordParentMap);

        if (!wordParentMap.containsKey(endWord)) {
            return List.of();
        }

        return evaluatePath(startWord, endWord, wordParentMap);
    }

    private void traverse(String startWord, String endWord, Set<String> validWords, Map<String, String> wordTransformPathMap) {
        for (int i = 0; i < startWord.length(); i++) {
            for (char c : LOWERCASE_EN_CHARS) {
                StringBuilder wordPermutator = new StringBuilder(startWord);
                wordPermutator.setCharAt(i, c);
                String testWord = wordPermutator.toString();
                if (validWords.contains(testWord)) {
                    // skip if we've previously seen this word
                    if (!wordTransformPathMap.containsKey(testWord)) {
                        wordTransformPathMap.put(testWord, startWord);
                        traverse(testWord, endWord, validWords, wordTransformPathMap);
                    }
                }
                if (wordTransformPathMap.containsKey(endWord)) {
                    return;
                }
            }
        }
    }

    private List<String> evaluatePath(String startWord, String endWord, Map<String, String> wordToParent) {
        List<String> path = new ArrayList<>();
        String currentWord = endWord;
        path.add(endWord);
        while (!currentWord.equals(startWord)) {
            currentWord = wordToParent.get(currentWord);
            path.add(currentWord);
        }
        Collections.reverse(path);
        return path;
    }
}
