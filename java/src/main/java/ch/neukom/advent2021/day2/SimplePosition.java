package ch.neukom.advent2021.day2;

public class SimplePosition {
    private final int depth;
    private final int horizontal;

    protected SimplePosition() {
        this.depth = 0;
        this.horizontal = 0;
    }

    private SimplePosition(int depth, int horizontal) {
        this.depth = depth;
        this.horizontal = horizontal;
    }

    public SimplePosition runCommand(Command command) {
        return switch (command.getType()) {
            case FORWARD -> new SimplePosition(depth, horizontal + command.getDistance());
            case DOWN -> new SimplePosition(depth + command.getDistance(), horizontal);
            case UP -> new SimplePosition(depth - command.getDistance(), horizontal);
        };
    }

    public SimplePosition combinePosition(SimplePosition position) {
        return new SimplePosition(depth + position.getDepth(), horizontal + position.getHorizontal());
    }

    public int getDepth() {
        return depth;
    }

    public int getHorizontal() {
        return horizontal;
    }
}
