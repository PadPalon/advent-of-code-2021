package ch.neukom.advent2021.day19;

public record Beacon(int x, int y, int z) {
    /**
     * any direction is fine for us, as long as it's always the same
     * just chose any of the 90-degree rotation matrices that aren't around the Z axis
     */
    public Beacon roll() {
        return new Beacon(x(), z(), -y());
    }

    /**
     * any direction is fine for us, as long as it's always the same
     * just chose any of the 90-degree rotation matrices that are around the Z axis
     */
    public Beacon turn() {
        return new Beacon(-y(), x(), z());
    }

    public Beacon move(Vector3 toMove) {
        return new Beacon(x + toMove.getX(), y + toMove.getY(), z + toMove.getZ());
    }
}
