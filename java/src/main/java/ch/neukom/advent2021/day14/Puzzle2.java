package ch.neukom.advent2021.day14;

import ch.neukom.advent2021.helper.InputResourceReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class Puzzle2 {
    public static final int ITERATIONS_TO_RUN = 40;

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            Map<String, Long> pairCounts = new HashMap<>();
            String input = reader.getFirstLine();
            for (int i = 0; i < input.length() - 1; i++) {
                pairCounts.put(input.substring(i, i + 2), 1L);
            }
            Map<String, String> rules = reader.readInput()
                    .skip(2)
                    .map(line -> line.split(" -> "))
                    .collect(toMap(split -> split[0], split -> split[1]));

            for (int i = 0; i < ITERATIONS_TO_RUN; i++) {
                applyInsertions(pairCounts, rules);
            }

            pairCounts.put(input.substring(input.length() - 1), 1L); // add a singular count of the last character of the input

            long[] characterCounts = pairCounts.entrySet()
                    .stream()
                    .collect(groupingBy(entry -> entry.getKey().charAt(0)))
                    .values()
                    .stream()
                    .map(Collection::stream)
                    .map(stream -> stream.mapToLong(Map.Entry::getValue))
                    .mapToLong(LongStream::sum)
                    .toArray();

            long mostCommonCount = Arrays.stream(characterCounts).max().orElseThrow();
            long leastCommonCount = Arrays.stream(characterCounts).min().orElseThrow();
            long difference = mostCommonCount - leastCommonCount;

            System.out.printf("The difference between the most and least common character counts is %s", difference);
        }
    }

    private static void applyInsertions(Map<String, Long> counts, Map<String, String> rules) {
        Map<String, Long> countsToIncrease = new HashMap<>();
        for (Map.Entry<String, String> ruleEntry : rules.entrySet()) {
            String key = ruleEntry.getKey();
            if (counts.containsKey(key)) {
                Long count = counts.get(key);
                decreaseCount(counts, key, count);
                String insertion = ruleEntry.getValue();
                increaseCount(countsToIncrease, ruleEntry.getKey().charAt(0) + insertion, count);
                increaseCount(countsToIncrease, insertion + ruleEntry.getKey().charAt(1), count);
            }
        }
        countsToIncrease.forEach((key, value) -> increaseCount(counts, key, value));
    }

    private static void increaseCount(Map<String, Long> counts, String key, Long amount) {
        counts.compute(key, (k, count) -> count == null ? amount : count + amount);
    }

    private static void decreaseCount(Map<String, Long> counts, String key, Long amount) {
        counts.compute(key, (k, count) -> count == null ? 0 : count - amount);
    }
}
