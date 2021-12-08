package ch.neukom.advent2021.day8;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;

import ch.neukom.advent2021.helper.InputResourceReader;
import com.google.common.base.Splitter;

import static java.util.stream.Collectors.*;

public class Puzzle1 {
    private static final Splitter SPACE_SPLITTER = Splitter.on(' ').trimResults().omitEmptyStrings();
    private static final Set<Integer> TARGET_NUMBERS = Set.of(1, 4, 7, 8);

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            long numberCount = reader.readInput()
                .map(Util.LINE_PATTERN::matcher)
                .filter(Matcher::matches)
                .mapToLong(Puzzle1::handleLine)
                .sum();
            System.out.printf("There are %s numbers", numberCount);
        }
    }

    private static long handleLine(Matcher lineMatcher) {
        ConnectionMatcher connectionMatcher = SPACE_SPLITTER.splitToStream(lineMatcher.group(1))
            .map(Util::getStringAsSet)
            .collect(collectingAndThen(toList(), ConnectionMatcher::setup));

        return SPACE_SPLITTER.splitToStream(lineMatcher.group(2))
            .map(Util::getStringAsSet)
            .mapToInt(connectionMatcher::findMatchingConnection)
            .filter(TARGET_NUMBERS::contains)
            .count();
    }
}
