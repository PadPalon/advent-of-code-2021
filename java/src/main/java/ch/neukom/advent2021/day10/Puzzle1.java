package ch.neukom.advent2021.day10;

import ch.neukom.advent2021.helper.InputResourceReader;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Puzzle1 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            int errorScore = reader.readInput()
                    .map(Puzzle1::getMismatchedParentheses)
                    .filter(mismatches -> !mismatches.isEmpty())
                    .map(mismatches -> mismatches.get(0))
                    .mapToInt(Parentheses::getErrorPoints)
                    .sum();
            System.out.printf("The total error score of the input is %s", errorScore);
        }
    }

    private static List<Parentheses> getMismatchedParentheses(String line) {
        Deque<Parentheses> stack = new ArrayDeque<>();
        List<Parentheses> mismatched = new ArrayList<>();
        for (char character : line.toCharArray()) {
            Parentheses.parseOpen(character).ifPresentOrElse(stack::push, () ->
                    Parentheses.parseClose(character).ifPresent(closingParentheses -> {
                        if (closingParentheses.equals(stack.peek())) {
                            stack.pop();
                        } else {
                            stack.clear();
                            mismatched.add(closingParentheses);
                        }
                    }));
        }
        return mismatched;
    }
}
