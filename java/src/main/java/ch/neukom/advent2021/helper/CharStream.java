package ch.neukom.advent2021.helper;

import java.util.stream.Stream;

public class CharStream {
    private CharStream() {
    }

    public static Stream<Character> of(String input) {
        Stream.Builder<Character> builder = Stream.builder();
        for (char c : input.toCharArray()) {
            builder.accept(c);
        }
        return builder.build();
    }
}
