package ch.neukom.advent2021.day2;

import java.io.IOException;
import java.util.regex.Matcher;

import ch.neukom.advent2021.helper.InputResourceReader;

import static ch.neukom.advent2021.day2.Command.*;

public class Puzzle2 {
    private Puzzle2() {
    }


    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            AimPosition finalPosition = reader.readInput()
                .map(COMMAND_PATTERN::matcher)
                .filter(Matcher::find)
                .map(match -> Command.parse(match.group(1), match.group(2)))
                .reduce(new AimPosition(), AimPosition::runCommand, AimPosition::combinePosition);
            System.out.printf("The solution is: %s", finalPosition.getHorizontal() * finalPosition.getDepth());
        }
    }
}
