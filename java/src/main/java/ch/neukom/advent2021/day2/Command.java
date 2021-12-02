package ch.neukom.advent2021.day2;

import java.util.regex.Pattern;

public record Command(CommandType type, int distance) {
    static final Pattern COMMAND_PATTERN = Pattern.compile("^(forward|down|up) ([0-9]*)$");

    static Command parse(String command, String distance) {
        return new Command(CommandType.valueOf(command.toUpperCase()), Integer.parseInt(distance));
    }

    public CommandType getType() {
        return type;
    }

    public int getDistance() {
        return distance;
    }
}
