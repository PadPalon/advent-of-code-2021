package ch.neukom.advent2021.day3;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import ch.neukom.advent2021.helper.InputResourceReader;

import static ch.neukom.advent2021.day3.Util.*;

public class Puzzle2 {
    private Puzzle2() {
    }

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            List<String> availableLines = reader.readInput().toList();
            int lineLength = reader.getFirstLine().length();

            Integer oxygen = findRating(availableLines, lineLength, Puzzle2::getMostCommonDigits);
            Integer co2 = findRating(availableLines, lineLength, Puzzle2::getLeastCommonDigits);

            System.out.printf("The life support rating is: %s", oxygen * co2);
        }
    }

    private static int[] getMostCommonDigits(List<String> lines, int inputSize) {
        return getAverages(lines, inputSize).mapToInt(average -> average >= 0.5 ? 1 : 0).toArray();
    }

    private static int[] getLeastCommonDigits(List<String> lines, int inputSize) {
        return getAverages(lines, inputSize).mapToInt(average -> average >= 0.5 ? 0 : 1).toArray();
    }

    private static DoubleStream getAverages(List<String> lines, int inputSize) {
        return IntStream.range(0, inputSize).mapToDouble(position -> getAverageAtPosition(lines.stream(), position));
    }

    private static Integer findRating(List<String> initialLines,
                                      int lineLength,
                                      BiFunction<List<String>, Integer, int[]> digitFunction) {
        List<String> availableLines = initialLines;
        AtomicInteger position = new AtomicInteger(0);
        while (availableLines.size() != 1) {
            int[] digits = digitFunction.apply(availableLines, lineLength);
            int currentPosition = position.getAndIncrement();
            char currentDigit = String.valueOf(digits[currentPosition]).charAt(0);
            availableLines = availableLines.stream()
                .filter(line -> line.charAt(currentPosition) == currentDigit)
                .toList();
        }
        return Integer.valueOf(availableLines.get(0), 2);
    }
}
