package ch.neukom.advent2021.day6;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import ch.neukom.advent2021.helper.InputResourceReader;

public class Puzzle1 {
    private static final int DAY_COUNT = 80;

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            List<Integer> state = Util.parseInitialState(reader.getFirstLine());
            for (int i = 0; i < DAY_COUNT; i++) {
                state = state.stream()
                    .map(Puzzle1::runDay)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            }
            System.out.printf("After %s days there are %s fish", DAY_COUNT, state.size());
        }
    }

    private static List<Integer> runDay(Integer life) {
        if (life == 0) {
            return List.of(6, 8);
        } else {
            return List.of(life - 1);
        }
    }
}
