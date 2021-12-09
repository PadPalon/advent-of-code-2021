package ch.neukom.advent2021.day9;

import java.util.HashSet;
import java.util.Set;

record Position(int x, int y, int height) {
    Set<Position> getNeighbours(int[][] map) {
        Set<Position> neighbours = new HashSet<>();
        if (x > 0) {
            neighbours.add(new Position(x - 1, y, map[x - 1][y]));
        }
        if (x < map.length - 1) {
            neighbours.add(new Position(x + 1, y, map[x + 1][y]));
        }
        if (y > 0) {
            neighbours.add(new Position(x, y - 1, map[x][y - 1]));
        }
        if (y < map[x].length - 1) {
            neighbours.add(new Position(x, y + 1, map[x][y + 1]));
        }
        return neighbours;
    }
}
