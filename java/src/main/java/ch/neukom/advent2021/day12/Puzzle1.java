package ch.neukom.advent2021.day12;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import ch.neukom.advent2021.helper.InputResourceReader;

import static ch.neukom.advent2021.day12.Util.*;

public class Puzzle1 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            Set<List<String>> uniquePaths = reader.readInput()
                .reduce(
                    new CaveGraph.Builder().withCavePredicate((currentPath, next) -> isUppercase(next) || !currentPath.contains(next)),
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
}
