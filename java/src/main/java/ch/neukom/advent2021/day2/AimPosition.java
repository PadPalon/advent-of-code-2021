package ch.neukom.advent2021.day2;

public class AimPosition {
    private final int depth;
    private final int horizontal;
    private final int aim;

    protected AimPosition() {
        this.depth = 0;
        this.horizontal = 0;
        this.aim = 0;
    }

    private AimPosition(int depth, int horizontal, int aim) {
        this.depth = depth;
        this.horizontal = horizontal;
        this.aim = aim;
    }

    public AimPosition runCommand(Command command) {
        return switch (command.getType()) {
            case FORWARD -> new AimPosition(depth + aim * command.getDistance(), horizontal + command.getDistance(), aim);
            case DOWN -> new AimPosition(depth, horizontal, aim + command.getDistance());
            case UP -> new AimPosition(depth, horizontal, aim - command.getDistance());
        };
    }

    public AimPosition combinePosition(AimPosition position) {
        return new AimPosition(depth + position.getDepth(), horizontal + position.getHorizontal(), aim + position.getAim());
    }

    public int getDepth() {
        return depth;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public int getAim() {
        return aim;
    }
}
