package com.duncantait.wordtransformer.helper;

import lombok.experimental.UtilityClass;

@UtilityClass
public class LogUtil {

    public static void logErrorAndThrow(String errorMessage) {
        System.out.println(errorMessage);
        throw new RuntimeException(errorMessage);
    }
}
