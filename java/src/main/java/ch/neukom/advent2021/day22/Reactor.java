package ch.neukom.advent2021.day22;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Reactor {
    private final Map<Position, Boolean> blocks = new HashMap<>();

    public Reactor(int positionStart, int positionEnd) {
        IntStream.rangeClosed(positionStart, positionEnd)
                .forEach(x -> IntStream.rangeClosed(positionStart, positionEnd)
                        .forEach(y -> IntStream.rangeClosed(positionStart, positionEnd)
                                .forEach(z -> blocks.put(new Position(x, y, z), false))));
    }

    public void runInstruction(Instruction instruction) {
        blocks.entrySet()
                .stream()
                .filter(block -> block.getKey().biggerEquals(instruction.start()))
                .filter(block -> block.getKey().smallerEquals(instruction.end()))
                .forEach(i -> i.setValue(instruction.value()));
    }

    public record Position(int x, int y, int z) {
        public boolean biggerEquals(Position position) {
            if (x < position.x()) {
                return false;
            }
            if (y < position.y()) {
                return false;
            }
            return z >= position.z();
        }

        public boolean smallerEquals(Position position) {
            if (x > position.x()) {
                return false;
            }
            if (y > position.y()) {
                return false;
            }
            return z <= position.z();
        }
    }

    public record Instruction(Position start, Position end, boolean value) {
    }
}
