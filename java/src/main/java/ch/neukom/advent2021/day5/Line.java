package ch.neukom.advent2021.day5;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Line(int x1, int y1, int x2, int y2) {
    private static final Pattern LINE_PATTERN = Pattern.compile("([0-9]*),([0-9]*) -> ([0-9]*),([0-9]*)");

    public static Line parse(String lineString) {
        return Optional.of(lineString)
            .map(LINE_PATTERN::matcher)
            .filter(Matcher::matches)
            .map(matcher -> new Line(
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3)),
                Integer.parseInt(matcher.group(4))
            ))
            .orElseThrow();
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public boolean isHorizontal() {
        return y1 == y2;
    }

    public boolean isVertical() {
        return x1 == x2;
    }
}
