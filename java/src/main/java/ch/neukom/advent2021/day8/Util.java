package ch.neukom.advent2021.day8;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Util {
    public static final Pattern LINE_PATTERN = Pattern.compile("((?:[a-z]+ ?)+) \\| ((?:[a-z]+ ?)+)");

    private Util() {
    }

    public static Set<Character> getStringAsSet(String string) {
        return string.chars().mapToObj(obj -> (char) obj).collect(Collectors.toSet());
    }
}
