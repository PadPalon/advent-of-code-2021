package ch.neukom.advent2021.day9;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.neukom.advent2021.helper.InputResourceReader;

public class Puzzle2 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            List<String> lines = reader.readInput().toList();

            int xSize = reader.getFirstLine().length();
            int ySize = lines.size();
            int[][] map = new int[xSize][ySize];
            for (int i = 0; i < ySize; i++) {
                map[i] = lines.get(i).chars().map(Util::charToInt).toArray();
            }

            Set<Position> handledPositions = new HashSet<>();
            Set<Basin> basins = new HashSet<>();
            for (int x = 0; x < xSize; x++) {
                for (int y = 0; y < ySize; y++) {
                    Position position = new Position(x, y, map[x][y]);

                    Basin basin = new Basin();
                    handlePosition(position, basin, handledPositions, map);
                    if (basin.size() > 0) {
                        basins.add(basin);
                    }
                }
            }

            int basinProduct = basins.stream()
                .mapToInt(Basin::size)
                .sorted()
                .skip(basins.size() - 3)
                .reduce((left, right) -> left * right)
                .orElseThrow();

            System.out.printf("The sizes of the three largest basins multiplied is %s", basinProduct);
        }
    }

    private static void handlePosition(Position position, Basin basin, Set<Position> handledPositions, int[][] map) {
        if (position.height() != 9 && !handledPositions.contains(position)) {
            basin.addPosition(position);
            handledPositions.add(position);
            position.getNeighbours(map).forEach(neighbour -> handlePosition(neighbour, basin, handledPositions, map));
        }
    }

    private static class Basin {
        private final Set<Position> positions = new HashSet<>();

        public void addPosition(Position position) {
            positions.add(position);
        }

        public int size() {
            return positions.size();
        }
    }
}
