package ch.neukom.advent2021.day5;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class VentMap {
    private final Table<Integer, Integer, Integer> vents = HashBasedTable.create();

    public VentMap addLine(Line line) {
        final int startX = line.getX1();
        final int startY = line.getY1();
        final int endX = line.getX2();
        final int endY = line.getY2();
        addVent(startX, startY);
        addVent(endX, endY);

        final int xDirection = (int) Math.signum(endX - startX);
        final int yDirection = (int) Math.signum(endY - startY);

        int currentX = startX + xDirection;
        int currentY = startY + yDirection;
        while (currentX != endX || currentY != endY) {
            addVent(currentX, currentY);
            currentX += xDirection;
            currentY += yDirection;
        }

        return this;
    }

    public VentMap combine(VentMap other) {
        VentMap combinedMap = new VentMap();
        this.vents.cellSet().forEach(cell -> IntStream.range(0, cell.getValue())
            .forEach(i -> combinedMap.addVent(cell.getRowKey(), cell.getColumnKey())));
        if (this != other) {
            other.vents.cellSet().forEach(cell -> IntStream.range(0, cell.getValue())
                .forEach(i -> combinedMap.addVent(cell.getRowKey(), cell.getColumnKey())));
        }
        return combinedMap;
    }

    public Set<MapPosition> getPositions() {
        return vents.cellSet()
            .stream()
            .map(cell -> new MapPosition(cell.getRowKey(), cell.getColumnKey(), cell.getValue()))
            .collect(Collectors.toSet());
    }

    private void addVent(int x, int y) {
        Integer currentVentStrength = Optional.ofNullable(vents.get(x, y)).map(i -> i + 1).orElse(1);
        vents.put(x, y, currentVentStrength);
    }

    protected static class MapPosition {
        private final int x;
        private final int y;
        private final int ventStrength;

        private MapPosition(int x, int y, int ventStrength) {
            this.x = x;
            this.y = y;
            this.ventStrength = ventStrength;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getVentStrength() {
            return ventStrength;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MapPosition that = (MapPosition) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
