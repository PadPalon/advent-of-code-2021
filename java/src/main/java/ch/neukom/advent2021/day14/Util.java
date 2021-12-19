package ch.neukom.advent2021.day14;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Util {
    private static final Pattern INSERTION_PATTERN = Pattern.compile("([A-Z]{2}) -> ([A-Z])");

    private Util() {
    }

    public static Map<String, String> parseInsertions(Stream<String> insertions) {
        return insertions.map(INSERTION_PATTERN::matcher)
            .filter(Matcher::matches)
            .collect(toMap(
                matcher -> matcher.group(1),
                matcher -> matcher.group(2)
            ));
    }
}
