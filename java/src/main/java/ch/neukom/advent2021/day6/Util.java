package ch.neukom.advent2021.day6;

import java.util.List;

import com.google.common.base.Splitter;

public class Util {
    private static final Splitter COMMA_SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();

    private Util() {
    }

    public static List<Integer> parseInitialState(String line) {
        return COMMA_SPLITTER.splitToStream(line)
            .map(Integer::parseInt)
            .toList();
    }
}
