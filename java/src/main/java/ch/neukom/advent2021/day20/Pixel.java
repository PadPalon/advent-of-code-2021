package ch.neukom.advent2021.day20;

import javax.annotation.Nullable;
import java.util.Optional;

public class Pixel {
    private final int x;
    private final int y;

    private boolean lit;
    @Nullable
    private Pixel top = null;
    @Nullable
    private Pixel bottom = null;
    @Nullable
    private Pixel left = null;
    @Nullable
    private Pixel right = null;

    public Pixel(int x, int y) {
        this(x, y, false);
    }

    public Pixel(int x, int y, boolean lit) {
        this.x = x;
        this.y = y;
        this.lit = lit;
    }

    public boolean isLit() {
        return lit;
    }

    public void setLit(boolean lit) {
        this.lit = lit;
    }

    public Optional<Pixel> getTop() {
        return Optional.ofNullable(top);
    }

    public Optional<Pixel> getBottom() {
        return Optional.ofNullable(bottom);
    }

    public Optional<Pixel> getLeft() {
        return Optional.ofNullable(left);
    }

    public Optional<Pixel> getRight() {
        return Optional.ofNullable(right);
    }

    public void setLeftNeighbour(Pixel pixel) {
        if (pixel == null) {
            return;
        }
        this.left = pixel;
        pixel.right = this;
    }

    public void setTopNeighbour(Pixel pixel) {
        if (pixel == null) {
            return;
        }
        this.top = pixel;
        pixel.bottom = this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("%s/%s %s", x, y, lit ? "#" : '.');
    }
}
