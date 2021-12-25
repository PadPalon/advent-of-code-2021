package ch.neukom.advent2021.day19;

public class Vector3 {
    private final int x;
    private final int y;
    private final int z;

    public Vector3(Beacon start, Beacon end) {
        this.x = start.x() - end.x();
        this.y = start.y() - end.y();
        this.z = start.z() - end.z();
    }

    public Vector3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int distanceTo(Vector3 other) {
        return Math.abs(x - other.getX()) + Math.abs(y - other.getY()) + Math.abs(z - other.getZ());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3 vector3 = (Vector3) o;

        if (x != vector3.x) return false;
        if (y != vector3.y) return false;
        return z == vector3.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}
