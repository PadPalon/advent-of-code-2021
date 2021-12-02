package ch.neukom.advent2021.day1;

import java.io.IOException;

import ch.neukom.advent2021.helper.InputResourceReader;

public class Puzzle1 {
    private Puzzle1() {
    }

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            int[] depths = reader.readInput().mapToInt(Integer::parseInt).toArray();
            int increaseCount = Util.getIncreaseCount(depths);
            System.out.printf("Result is: %s%n", increaseCount);
        }
    }
}
