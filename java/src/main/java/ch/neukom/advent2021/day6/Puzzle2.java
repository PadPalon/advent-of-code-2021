package ch.neukom.advent2021.day6;

import java.io.IOException;
import java.util.Arrays;

import ch.neukom.advent2021.helper.InputResourceReader;

/**
 * this is just an optimized version of {@link Puzzle1} that aims to use primitives and as few operations as possible
 * <p>
 * I have a feeling that there is an easier way to calculate this with the power of math, but brain no work good
 */
public class Puzzle2 {
    private static final int DAY_COUNT = 256;
    private static final int BUFFER_SIZE = 9;

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            long[] fishes = new long[BUFFER_SIZE];
            Util.parseInitialState(reader.getFirstLine()).forEach(i -> fishes[i]++);
            for (int i = 0; i < DAY_COUNT; i++) {
                int currentIndex = i % BUFFER_SIZE;
                long spawnCount = fishes[currentIndex];
                fishes[currentIndex] = 0;
                fishes[(currentIndex + 7) % BUFFER_SIZE] += spawnCount;
                fishes[(currentIndex + 9) % BUFFER_SIZE] += spawnCount;
            }
            System.out.printf("After %s days there are %s fish", DAY_COUNT, Arrays.stream(fishes).sum());
        }
    }
}
