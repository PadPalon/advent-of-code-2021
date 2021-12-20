package ch.neukom.advent2021.day15;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

public class AStar {
    private final int[][] map;

    private AStar(int[][] map) {
        this.map = map;
    }

    public static AStar parseMap(int width, int height, Stream<String> input, int mapFactor) {
        int[][] map = new int[width * mapFactor][height * mapFactor];

        AtomicInteger index = new AtomicInteger(0);
        input.flatMap(line -> line.chars().mapToObj(Util::charToInt))
            .forEach(value -> {
                int current = index.getAndIncrement();
                int y = current / width;
                int x = current - y * width;
                for (int xFactor = 0; xFactor < mapFactor; xFactor++) {
                    for (int yFactor = 0; yFactor < mapFactor; yFactor++) {
                        int scaledValue = value + xFactor + yFactor;
                        if (scaledValue >= 10) {
                            scaledValue = scaledValue % 10 + 1;
                        }
                        map[x + xFactor * width][y + yFactor * height] = scaledValue;
                    }
                }
            });

        return new AStar(map);
    }

    public List<Position> findSafestPath(Position startPosition, Position endPosition) {
        Set<Position> toHandle = new HashSet<>();
        toHandle.add(startPosition);
        Map<Position, Position> previous = new HashMap<>();
        Map<Position, Integer> shortestPaths = new HashMap<>();
        shortestPaths.put(startPosition, 0);
        Map<Position, Integer> pathScores = new HashMap<>();
        pathScores.put(startPosition, calculatePositionDistance(startPosition, endPosition));

        while (!toHandle.isEmpty()) {
            Position current = toHandle.stream()
                .min(Comparator.comparing(node -> pathScores.computeIfAbsent(node, k -> Integer.MAX_VALUE)))
                .orElseThrow();
            if (current.equals(endPosition)) {
                return reconstructPath(previous, endPosition);
            }

            toHandle.remove(current);

            Set<Position> neighbours = getNeighbours(current, map);
            for (Position neighbour : neighbours) {
                int score = shortestPaths.computeIfAbsent(current, k -> Integer.MAX_VALUE) + neighbour.risk(map);
                if (score < shortestPaths.computeIfAbsent(neighbour, k -> Integer.MAX_VALUE)) {
                    previous.put(neighbour, current);
                    shortestPaths.put(neighbour, score);
                    pathScores.put(neighbour, score + calculatePositionDistance(neighbour, endPosition));
                    toHandle.add(neighbour);
                }
            }
        }

        throw new IllegalStateException("Could not find path");
    }

    public int calculateRiskOfPath(List<Position> path) {
        return path.stream()
            .filter(pos -> pos.x() != 0 || pos.y() != 0)
            .mapToInt(pos -> pos.risk(map))
            .sum();
    }

    private int calculatePositionDistance(Position start, Position end) {
        return end.x() - start.x() + end.y() - start.y();
    }

    private List<Position> reconstructPath(Map<Position, Position> previous, Position endPosition) {
        ImmutableList.Builder<Position> pathBuilder = ImmutableList.<Position>builder().add(endPosition);
        Position current = endPosition;
        while (previous.containsKey(current)) {
            current = previous.get(current);
            pathBuilder.add(current);
        }
        return pathBuilder.build();
    }

    private Set<Position> getNeighbours(Position current, int[][] map) {
        Set<Position> neighbours = new HashSet<>();
        int x = current.x();
        int y = current.y();
        if (x > 0) {
            neighbours.add(new Position(x - 1, y));
        }
        if (x < map.length - 1) {
            neighbours.add(new Position(x + 1, y));
        }
        if (y > 0) {
            neighbours.add(new Position(x, y - 1));
        }
        if (y < map[x].length - 1) {
            neighbours.add(new Position(x, y + 1));
        }
        return neighbours;
    }

    public record Position(int x, int y) {
        public int risk(int[][] map) {
            return map[x][y];
        }
    }
}
