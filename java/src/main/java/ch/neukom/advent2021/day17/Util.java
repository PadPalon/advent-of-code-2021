package ch.neukom.advent2021.day17;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final Pattern INPUT_PATTERN = Pattern.compile("target area: x=(-*[0-9]+)..(-*[0-9]+), y=(-*[0-9]+)..(-*[0-9]+)");

    private Util() {
    }

    public static int[] getTargetAreaPoints(String input) {
        Matcher matcher = INPUT_PATTERN.matcher(input);
        if (matcher.matches()) {
            int xMin = Integer.parseInt(matcher.group(1));
            int xMax = Integer.parseInt(matcher.group(2));
            int yMin = Integer.parseInt(matcher.group(3));
            int yMax = Integer.parseInt(matcher.group(4));
            return new int[]{xMin, xMax, yMin, yMax};
        } else {
            throw new IllegalStateException("Could not load target area");
        }
    }
}
