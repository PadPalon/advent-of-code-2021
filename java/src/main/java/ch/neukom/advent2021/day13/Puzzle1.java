package ch.neukom.advent2021.day13;

import java.io.IOException;
import java.util.regex.Matcher;

import ch.neukom.advent2021.helper.InputResourceReader;

import static ch.neukom.advent2021.day13.Page.*;

public class Puzzle1 {

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            Page page = Page.build(reader.readInput());

            reader.readInput()
                .dropWhile(line -> !line.isEmpty())
                .skip(1)
                .findFirst()
                .map(FOLD_PATTERN::matcher)
                .filter(Matcher::matches)
                .ifPresent(matcher -> {
                    String direction = matcher.group(1);
                    int foldPosition = Integer.parseInt(matcher.group(2));
                    page.fold(direction, foldPosition);
                });

            System.out.printf("There are %s dots on the page after the first fold.", page.getDotCount());
        }
    }
}
