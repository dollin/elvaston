package com.dollin.albury.selection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class Selection {
    private static Logger LOG;

    private static final String YEAR = "U12s";
    private static final String DATA = "\\elvaston-selection\\src\\main\\resources\\Albury" + YEAR + ".dat";

    private String team;

    public Selection(String team) {
        this.team = team;
    }

    public static void main(String... args) {
        System.setProperty("filename", Selection.class.getName());

        LOG = LogManager.getLogger(Selection.class);

        new Selection(YEAR).runSelection();
    }

    private void runSelection() {
        try (Stream<String> stream = Files.lines(Paths.get(System.getProperty("user.dir") + DATA))) {
            stream.map(s -> new Player(team, s)).sorted().filter(Player::outfield).forEach(LOG::info);
        } catch (IOException e) {
            LOG.warn("Exception caught reading the dat file", e);
        }

        System.out.println("");
        try (Stream<String> stream = Files.lines(Paths.get(System.getProperty("user.dir") + DATA))) {
            stream.map(s -> new Player(team, s)).sorted().forEach(System.out::println);
        } catch (IOException e) {
            LOG.warn("Exception caught reading the dat file", e);
        }
    }
}