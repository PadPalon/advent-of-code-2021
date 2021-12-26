package ch.neukom.advent2021.day22;

import ch.neukom.advent2021.helper.InputResourceReader;
import com.google.common.collect.Range;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle1 {
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("(on|off) x=(-*[0-9]+\\.\\.-*[0-9]+),y=(-*[0-9]+\\.\\.-*[0-9]+),z=(-*[0-9]+\\.\\.-*[0-9]+)");
    private static final Pattern COORDINATE_PATTERN = Pattern.compile("(-*[0-9]+)\\.\\.(-*[0-9]+)");

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            Reactor reactorEndState = reader.readInput()
                    .map(INSTRUCTION_PATTERN::matcher)
                    .filter(Matcher::matches)
                    .map(matcher -> {
                        Range<Integer> xRange = getXRange(matcher.group(2));
                        Range<Integer> yRange = getXRange(matcher.group(3));
                        Range<Integer> zRange = getXRange(matcher.group(4));
                        return new Reactor.Instruction(
                                new Reactor.Position(xRange.lowerEndpoint(), yRange.lowerEndpoint(), zRange.lowerEndpoint()),
                                new Reactor.Position(xRange.upperEndpoint(), yRange.upperEndpoint(), zRange.upperEndpoint()),
                                matcher.group(1).equals("on")
                        );
                    })
                    .reduce(new Reactor(-50, 50), (reactor, instruction) -> {
                        reactor.runInstruction(instruction);
                        return reactor;
                    }, (reactor, reactor2) -> {
                        throw new IllegalStateException("don't");
                    });
            System.out.printf("test");
        }
    }

    private static Range<Integer> getXRange(String coordString) {
        Matcher matcher = COORDINATE_PATTERN.matcher(coordString);
        Range<Integer> range;
        if (matcher.matches()) {
            int start = Integer.parseInt(matcher.group(1));
            int end = Integer.parseInt(matcher.group(2));
            range = Range.closed(start, end);
        } else {
            throw new IllegalStateException("Could not load instruction position");
        }
        return range;
    }
}
