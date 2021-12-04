package ch.neukom.advent2021.day4;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import com.google.common.base.Splitter;

public class BingoBoard {
    public static final Splitter SPACE_SPLITTER = Splitter.on(' ').trimResults().omitEmptyStrings();
    public static final Splitter NEW_LINE_SPLITTER = Splitter.on('\n').trimResults().omitEmptyStrings();

    private final MarkableNumber[][] numbers = new MarkableNumber[5][5];

    private BingoBoard() {
    }

    public static BingoBoard parse(String boardString) {
        BingoBoard board = new BingoBoard();
        AtomicInteger xIndex = new AtomicInteger();
        NEW_LINE_SPLITTER.splitToStream(boardString).forEach(line -> {
            int xPosition = xIndex.getAndIncrement();
            AtomicInteger yIndex = new AtomicInteger();
            SPACE_SPLITTER.splitToStream(line)
                .mapToInt(Integer::parseInt)
                .forEach(number -> board.numbers[xPosition][yIndex.getAndIncrement()] = new MarkableNumber(number));
        });
        return board;
    }

    public BingoBoard mark(int bingoNumber) {
        return setMark(bingoNumber, MarkableNumber::mark);
    }

    public boolean hasWon() {
        return hasFullLines((x, y) -> numbers[x][y]) || hasFullLines((x, y) -> numbers[y][x]);
    }

    public IntStream getUnmarked() {
        return Arrays.stream(numbers)
            .flatMap(Arrays::stream)
            .filter(MarkableNumber::isUnmarked)
            .mapToInt(MarkableNumber::getNumber);
    }

    private BingoBoard setMark(int bingoNumber, Consumer<MarkableNumber> consumer) {
        Arrays.stream(numbers)
            .flatMap(Arrays::stream)
            .filter(number -> number.getNumber() == bingoNumber)
            .forEach(consumer);
        return this;
    }

    private boolean hasFullLines(BiFunction<Integer, Integer, MarkableNumber> function) {
        for (int x = 0; x < 5; x++) {
            boolean isComplete = true;
            for (int y = 0; y < 5; y++) {
                if (function.apply(x, y).isUnmarked()) {
                    isComplete = false;
                    break;
                }
            }
            if (isComplete) {
                return true;
            }
        }
        return false;
    }

    private static class MarkableNumber {
        private final int number;
        private boolean marked;

        public MarkableNumber(int number) {
            this.number = number;
            this.marked = false;
        }

        public int getNumber() {
            return number;
        }

        public void mark() {
            marked = true;
        }

        public boolean isUnmarked() {
            return !marked;
        }
    }
}
