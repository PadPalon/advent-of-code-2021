package ch.neukom.advent2021.day10;

import ch.neukom.advent2021.helper.InputResourceReader;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class Puzzle2 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            long[] completionScores = reader.readInput()
                    .map(Puzzle2::getParentheses)
                    .filter(stack -> !stack.isEmpty())
                    .mapToLong(Puzzle2::calculateCompletionScore)
                    .sorted()
                    .toArray();
            long bestCompletionScore = completionScores[completionScores.length / 2];

            System.out.printf("The best completion score of the input is %s", bestCompletionScore);
        }
    }

    private static Deque<Parentheses> getParentheses(String line) {
        Deque<Parentheses> stack = new ArrayDeque<>();
        for (char character : line.toCharArray()) {
            Optional<Parentheses> open = Parentheses.parseOpen(character);
            if (open.isPresent()) {
                stack.push(open.get());
            } else {
                Optional<Parentheses> close = Parentheses.parseClose(character);
                if (close.isPresent() && close.get().equals(stack.peek())) {
                    stack.pop();
                } else {
                    stack.clear();
                    break;
                }
            }
        }
        return stack;
    }

    private static long calculateCompletionScore(Deque<Parentheses> stack) {
        return stack.stream()
                .mapToLong(Parentheses::getCompletionPoints)
                .reduce(0, (left, right) -> left * 5 + right);
    }
}
