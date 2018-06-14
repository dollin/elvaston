package com.dollin.albury.selection.rules.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Rules {

    /**
     * Rules to use during selection.
     * @param yearGroup Say U12s to get the rule set
     * @return A stream of rules to process
     */
    public static Stream<String> get(String yearGroup) {
        Stream<String> rules = null;
        try {
            rules = Files.lines(Paths.get(System.getProperty("user.dir")
                    + "\\elvaston-selection\\src\\main\\resources\\Albury" + yearGroup + ".rules"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rules;
    }
}
