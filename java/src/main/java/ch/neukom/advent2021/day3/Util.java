package ch.neukom.advent2021.day3;

import java.util.stream.Stream;

public class Util {
    private Util() {
    }

    public static double getAverageAtPosition(Stream<String> input, int position) {
        return input.map(line -> line.charAt(position))
            .map(Object::toString)
            .mapToInt(Integer::parseInt)
            .average()
            .orElseThrow();
    }
}
