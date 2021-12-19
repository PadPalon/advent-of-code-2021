package ch.neukom.advent2021.day14;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import ch.neukom.advent2021.helper.InputResourceReader;

import static java.util.stream.Collectors.*;

public class Puzzle1 {
    private static final int ITERATIONS_TO_RUN = 10;

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            Map<String, String> insertions = Util.parseInsertions(reader.readInput().skip(2));
            Polymerizer finalState = IntStream.range(0, ITERATIONS_TO_RUN)
                .boxed()
                .reduce(
                    new Polymerizer(reader.getFirstLine()),
                    (current, i) -> current.runInsertions(insertions),
                    (l, r) -> {
                        throw new IllegalStateException("Do not run this in parallel");
                    }
                );

            int[] characterCounts = finalState.getOutput()
                .chars()
                .boxed()
                .collect(groupingBy(i -> i))
                .values()
                .stream()
                .mapToInt(List::size)
                .sorted()
                .toArray();

            int difference = characterCounts[characterCounts.length - 1] - characterCounts[0];

            System.out.printf("The difference between the counts of the most and least common characters is %s%n", difference);
        }
    }
}
