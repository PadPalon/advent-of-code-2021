package ch.neukom.advent2021.day5;

import java.io.IOException;

import ch.neukom.advent2021.helper.InputResourceReader;

public class Puzzle2 {
    private Puzzle2() {
    }

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            long strongVentCount = reader.readInput()
                .map(Line::parse)
                .reduce(new VentMap(), VentMap::addLine, VentMap::combine)
                .getPositions()
                .stream()
                .filter(position -> position.getVentStrength() > 1)
                .count();
            System.out.printf("At %s positions there are at least two overlapping lines", strongVentCount);
        }
    }
}
