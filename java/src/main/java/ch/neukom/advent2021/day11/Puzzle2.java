package ch.neukom.advent2021.day11;

import java.io.IOException;

import ch.neukom.advent2021.helper.InputResourceReader;

import static java.util.stream.Collectors.*;

public class Puzzle2 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            OctopusMap octopi = reader.readInput()
                .map(line -> line.chars().mapToObj(Util::charToInt).toList())
                .collect(collectingAndThen(toList(), OctopusMap::build));

            int step = 1;
            while (octopi.runStep() != octopi.size()) {
                step++;
            }
            System.out.printf("The first turn where all octopi flash is turn number %s", step);
        }
    }

}
