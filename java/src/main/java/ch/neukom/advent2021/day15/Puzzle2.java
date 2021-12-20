package ch.neukom.advent2021.day15;

import java.io.IOException;
import java.util.List;

import ch.neukom.advent2021.day15.AStar.Position;
import ch.neukom.advent2021.helper.InputResourceReader;

public class Puzzle2 {
    public static final int MAP_FACTOR = 5;

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            int width = reader.getFirstLine().length();
            int height = (int) reader.getLineCount();

            AStar map = AStar.parseMap(width, height, reader.readInput(), MAP_FACTOR);
            List<Position> safestPath = map.findSafestPath(
                new Position(0, 0),
                new Position(width * MAP_FACTOR - 1, height * MAP_FACTOR - 1)
            );
            int totalRisk = map.calculateRiskOfPath(safestPath);

            System.out.printf("The smallest risk through the cave is %s", totalRisk);
        }
    }
}
