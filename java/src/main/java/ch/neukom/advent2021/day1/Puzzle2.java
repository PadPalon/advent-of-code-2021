package ch.neukom.advent2021.day1;

import java.io.IOException;
import java.util.stream.IntStream;

import ch.neukom.advent2021.helper.InputResourceReader;

public class Puzzle2 {
    private Puzzle2() {
    }

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            int[] depths = reader.readInput().mapToInt(Integer::parseInt).toArray();
            int[] windowSums = IntStream.range(0, depths.length - 2)
                .map(i -> depths[i] + depths[i + 1] + depths[i + 2])
                .toArray();

            int increaseCount = Util.getIncreaseCount(windowSums);
            System.out.printf("Result is: %s%n", increaseCount);
        }
    }
}
