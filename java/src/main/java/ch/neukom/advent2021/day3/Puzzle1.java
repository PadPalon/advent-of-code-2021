package ch.neukom.advent2021.day3;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import ch.neukom.advent2021.helper.InputResourceReader;

import static ch.neukom.advent2021.day3.Util.*;
import static java.util.stream.Collectors.*;

public class Puzzle1 {
    private Puzzle1() {
    }

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            int inputSize = reader.getFirstLine().length();
            int[] gammaDigits = getMostCommonDigits(reader, inputSize);
            int[] epsilonDigits = flipBits(gammaDigits);

            Integer gamma = parseBinaryDigits(gammaDigits);
            Integer epsilon = parseBinaryDigits(epsilonDigits);
            System.out.printf("The power consumption is: %s", gamma * epsilon);
        }
    }

    private static int[] getMostCommonDigits(InputResourceReader reader, int inputSize) {
        return IntStream.range(0, inputSize)
            .mapToDouble(position -> getAverageAtPosition(reader.readInput(), position))
            .mapToInt(average -> average > 0.5 ? 1 : 0)
            .toArray();
    }

    private static int[] flipBits(int[] gammaDigits) {
        return Arrays.stream(gammaDigits)
            .map(digit -> digit * -1)
            .map(digit -> digit + 1)
            .toArray();
    }

    private static Integer parseBinaryDigits(int[] epsilonDigits) {
        return Arrays.stream(epsilonDigits)
            .mapToObj(String::valueOf)
            .collect(collectingAndThen(joining(), digits -> Integer.valueOf(digits, 2)));
    }
}
