package ch.neukom.advent2021.day7;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import ch.neukom.advent2021.helper.InputResourceReader;
import com.google.common.base.Splitter;

public class Puzzle1 {
    private static final Splitter COMMA_SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            int[] startingLevels = COMMA_SPLITTER.splitToStream(reader.getFirstLine())
                .mapToInt(Integer::parseInt)
                .toArray();
            int minLevel = Arrays.stream(startingLevels).min().orElseThrow();
            int maxLevel = Arrays.stream(startingLevels).max().orElseThrow();

            int minimumFuelUse = IntStream.rangeClosed(minLevel, maxLevel)
                .map(level -> calculateFuelUse(startingLevels, level))
                .min()
                .orElseThrow();

            System.out.printf("The crabs must use at least %s fuel", minimumFuelUse);
        }
    }

    private static int calculateFuelUse(int[] startingLevels, int level) {
        return Arrays.stream(startingLevels)
            .map(i -> level - i)
            .map(Math::abs)
            .sum();
    }
}
