package ch.neukom.advent2021.day10;

import java.util.Arrays;
import java.util.Optional;

public enum Parentheses {
    ROUND('(', ')', 3, 1),
    SQUARE('[', ']', 57, 2),
    CURLY('{', '}', 1197, 3),
    ANGLE('<', '>', 25137, 4);

    private final char open;
    private final char close;
    private final int errorPoints;
    private final int completionPoints;

    Parentheses(char open, char close, int errorPoints, int completionPoints) {
        this.open = open;
        this.close = close;
        this.errorPoints = errorPoints;
        this.completionPoints = completionPoints;
    }

    static Optional<Parentheses> parseOpen(char character) {
        return Arrays.stream(Parentheses.values())
                .filter(p -> p.open == character)
                .findAny();
    }

    static Optional<Parentheses> parseClose(char character) {
        return Arrays.stream(Parentheses.values())
                .filter(p -> p.close == character)
                .findAny();
    }

    int getErrorPoints() {
        return errorPoints;
    }

    public int getCompletionPoints() {
        return completionPoints;
    }
}
