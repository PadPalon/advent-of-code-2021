package ch.neukom.advent2021.day18;

import ch.neukom.advent2021.helper.InputResourceReader;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

public class Puzzle2 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            long biggestMagnitude = reader.readInput()
                    .map(SnailNumberReader::new)
                    .map(SnailNumberReader::parse)
                    .map(SnailNumber::reduce)
                    .collect(collectingAndThen(toSet(), Puzzle2::getCombinations))
                    .stream()
                    .mapToLong(SnailNumber::magnitude)
                    .max()
                    .orElseThrow();
            System.out.printf("The biggest possible magnitude is %s", biggestMagnitude);
        }
    }

    private static Set<SnailNumber> getCombinations(Set<SnailNumber> numbers) {
        Set<SnailNumber> combinations = new HashSet<>();
        for (SnailNumber left : numbers) {
            for (SnailNumber right : numbers) {
                if (!left.equals(right)) {
                    combinations.add(left.add(right));
                }
            }
        }
        return combinations;
    }
}
