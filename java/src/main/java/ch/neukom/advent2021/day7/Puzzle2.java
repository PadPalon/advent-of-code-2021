package ch.neukom.advent2021.day7;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import ch.neukom.advent2021.helper.InputResourceReader;
import com.google.common.base.Splitter;

public class Puzzle2 {
    private static final Splitter COMMA_SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            int[] startingLevels = COMMA_SPLITTER.splitToStream(reader.getFirstLine())
                .mapToInt(Integer::parseInt)
                .toArray();
            int minLevel = Arrays.stream(startingLevels).min().orElseThrow();
            int maxLevel = Arrays.stream(startingLevels).max().orElseThrow();

            double minimumFuelUse = IntStream.rangeClosed(minLevel, maxLevel)
                .mapToDouble(level -> calculateFuelUse(startingLevels, level))
                .min()
                .orElseThrow();

            System.out.printf("The crabs must use at least %.0f fuel", minimumFuelUse);
        }
    }

    private static double calculateFuelUse(int[] startingLevels, int level) {
        return Arrays.stream(startingLevels)
            .map(i -> level - i)
            .map(Math::abs)
            .mapToDouble(Puzzle2::calculateTriangularNumber)
            .sum();
    }

    private static double calculateTriangularNumber(int i) {
        return (Math.pow(i, 2) + i) / 2;
    }
}
