package ch.neukom.advent2021.day11;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class OctopusMap {
    private final Octopus[][] octopi;
    private final int width;
    private final int height;

    private OctopusMap(Octopus[][] octopi) {
        this.octopi = octopi;
        this.width = octopi.length;
        this.height = octopi[0].length;
    }

    public static OctopusMap build(List<List<Integer>> initialState) {
        int height = initialState.size();
        int width = initialState.get(0).size();
        Octopus[][] initialOctopi = new Octopus[width][height];
        for (int y = 0; y < height; y++) {
            List<Integer> line = initialState.get(y);
            for (int x = 0; x < width; x++) {
                Integer initialLevel = line.get(x);
                initialOctopi[x][y] = new Octopus(x, y, initialLevel);
            }
        }
        return new OctopusMap(initialOctopi);
    }

    public long runStep() {
        Set<Octopus> allFlashedOctopi = new HashSet<>();
        Deque<Octopus> flashingOctopi = new ArrayDeque<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Octopus currentOctopus = octopi[x][y];
                if (currentOctopus.increase()) {
                    flashingOctopi.push(currentOctopus);
                    allFlashedOctopi.add(currentOctopus);
                }
            }
        }
        while (!flashingOctopi.isEmpty()) {
            Octopus currentOctopus = flashingOctopi.removeLast();
            int x = currentOctopus.getX();
            int y = currentOctopus.getY();
            getNeighbours(x, y)
                .filter(Octopus::increase)
                .forEach(o -> {
                    flashingOctopi.push(o);
                    allFlashedOctopi.add(o);
                });
        }

        allFlashedOctopi.forEach(Octopus::discharge);
        return allFlashedOctopi.size();
    }

    public long size() {
        return (long) width * height;
    }

    private Stream<Octopus> getNeighbours(int x, int y) {
        Stream.Builder<Octopus> streamBuilder = Stream.builder();
        if (x > 0) {
            streamBuilder.accept(octopi[x - 1][y]);
        }
        if (x > 0 && y > 0) {
            streamBuilder.accept(octopi[x - 1][y - 1]);
        }
        if (x < width - 1) {
            streamBuilder.accept(octopi[x + 1][y]);
        }
        if (x < width - 1 && y > 0) {
            streamBuilder.accept(octopi[x + 1][y - 1]);
        }
        if (x > 0 && y < height - 1) {
            streamBuilder.accept(octopi[x - 1][y + 1]);
        }
        if (x < width - 1 && y < height - 1) {
            streamBuilder.accept(octopi[x + 1][y + 1]);
        }
        if (y > 0) {
            streamBuilder.accept(octopi[x][y - 1]);
        }
        if (y < height - 1) {
            streamBuilder.accept(octopi[x][y + 1]);
        }
        return streamBuilder.build();
    }

    private static class Octopus {
        private final int x;
        private final int y;

        private int energyLevel;

        private Octopus(int x, int y, int energyLevel) {
            this.x = x;
            this.y = y;
            this.energyLevel = energyLevel;
        }

        public boolean increase() {
            return energyLevel++ == 9;
        }

        public void discharge() {
            energyLevel = 0;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
