package ch.neukom.advent2021.day12;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ch.neukom.advent2021.helper.InputResourceReader;

import static ch.neukom.advent2021.day12.Util.*;

public class Puzzle2 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            Set<List<String>> uniquePaths = reader.readInput()
                .reduce(
                    new CaveGraph.Builder().withCavePredicate((currentPath, next) -> isUppercase(next) || isValidSmallCave(currentPath, next)),
                    CaveGraph.Builder::addConnection,
                    (left, right) -> {
                        throw new IllegalStateException("Just don't run this in parallel lol");
                    }
                )
                .build()
                .getUniquePaths("start", "end");
            System.out.printf("There are %s unique paths from start to end", uniquePaths.size());
        }
    }

    private static boolean isValidSmallCave(List<String> currentPath, String next) {
        if (next.equals("start")) {
            return false;
        }

        boolean smallCaveVisitedTwice = currentPath.stream()
            .filter(Util::isLowercase)
            .collect(Collectors.groupingBy(path -> path))
            .values()
            .stream()
            .anyMatch(positions -> positions.size() > 1);
        if (smallCaveVisitedTwice) {
            return !currentPath.contains(next);
        } else {
            return true;
        }
    }
}
