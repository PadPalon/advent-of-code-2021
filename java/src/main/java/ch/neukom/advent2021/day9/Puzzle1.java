package ch.neukom.advent2021.day9;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.neukom.advent2021.helper.InputResourceReader;

public class Puzzle1 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            List<String> lines = reader.readInput().toList();

            int xSize = reader.getFirstLine().length();
            int ySize = lines.size();
            int[][] map = new int[xSize][ySize];
            for (int i = 0; i < ySize; i++) {
                map[i] = lines.get(i).chars().map(Util::charToInt).toArray();
            }

            Set<Position> lowPoints = new HashSet<>();
            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    Position position = new Position(x, y, map[x][y]);
                    boolean isLowest = true;
                    for (Position neighbour : position.getNeighbours(map)) {
                        isLowest = isLowest && position.height() < map[neighbour.x()][neighbour.y()];
                    }
                    if (isLowest) {
                        lowPoints.add(position);
                    }
                }
            }

            int sum = lowPoints.stream().mapToInt(Position::height).map(i -> i + 1).sum();
            System.out.printf("The sum of all the low points is %s", sum);
        }
    }
}
