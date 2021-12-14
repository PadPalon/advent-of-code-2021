package ch.neukom.advent2021.day11;

import java.io.IOException;
import java.util.stream.IntStream;

import ch.neukom.advent2021.helper.InputResourceReader;

import static java.util.stream.Collectors.*;

public class Puzzle1 {
    private static final int TARGET_STEPS = 100;

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            OctopusMap octopi = reader.readInput()
                .map(line -> line.chars().mapToObj(Util::charToInt).toList())
                .collect(collectingAndThen(toList(), OctopusMap::build));

            long numberOfFlashes = IntStream.range(0, TARGET_STEPS)
                .mapToLong(i -> octopi.runStep())
                .sum();

            System.out.printf("After %s steps, %s octopi will have flashed", TARGET_STEPS, numberOfFlashes);
        }
    }
}
