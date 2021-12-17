package ch.neukom.advent2021.day13;

import java.io.IOException;
import java.util.regex.Matcher;

import ch.neukom.advent2021.helper.InputResourceReader;

import static ch.neukom.advent2021.day13.Page.*;

public class Puzzle2 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            Page page = Page.build(reader.readInput());

            reader.readInput()
                .dropWhile(line -> !line.isEmpty())
                .skip(1)
                .map(FOLD_PATTERN::matcher)
                .filter(Matcher::matches)
                .forEach(matcher -> {
                    String direction = matcher.group(1);
                    int foldPosition = Integer.parseInt(matcher.group(2));
                    page.fold(direction, foldPosition);
                });

            System.out.println(page.formatPage());
        }
    }
}
