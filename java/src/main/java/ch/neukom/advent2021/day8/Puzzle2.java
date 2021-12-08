package ch.neukom.advent2021.day8;

import java.io.IOException;
import java.util.regex.Matcher;

import ch.neukom.advent2021.helper.InputResourceReader;
import com.google.common.base.Splitter;

import static java.util.stream.Collectors.*;

public class Puzzle2 {
    private static final Splitter SPACE_SPLITTER = Splitter.on(' ').trimResults().omitEmptyStrings();

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            long numberSum = reader.readInput()
                .map(Util.LINE_PATTERN::matcher)
                .filter(Matcher::matches)
                .mapToInt(Puzzle2::handleLine)
                .sum();
            System.out.printf("The sum of all numbers is %s", numberSum);
        }
    }

    private static int handleLine(Matcher lineMatcher) {
        ConnectionMatcher connectionMatcher = SPACE_SPLITTER.splitToStream(lineMatcher.group(1))
            .map(Util::getStringAsSet)
            .collect(collectingAndThen(toList(), ConnectionMatcher::setup));

        return SPACE_SPLITTER.splitToStream(lineMatcher.group(2))
            .map(Util::getStringAsSet)
            .map(connectionMatcher::findMatchingConnection)
            .map(String::valueOf)
            .collect(collectingAndThen(joining(), Integer::parseInt));
    }
}
