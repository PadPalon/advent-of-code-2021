package ch.neukom.advent2021.day4;

import java.io.IOException;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import ch.neukom.advent2021.helper.InputResourceReader;
import com.google.common.base.Splitter;

import static java.util.stream.Collectors.*;

public class Puzzle2 {

    public static final Splitter PART_SPLITTER = Splitter.on("\n\n").omitEmptyStrings().trimResults();
    public static final Splitter COMMA_SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();

    private Puzzle2() {
    }

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            String input = reader.readInput().collect(joining("\n"));
            List<String> parts = PART_SPLITTER.splitToList(input);
            int[] bingoNumbers = COMMA_SPLITTER.splitToStream(parts.get(0)).mapToInt(Integer::parseInt).toArray();
            List<BingoBoard> bingoBoards = parts.stream().skip(1).map(BingoBoard::parse).toList();
            for (int bingoNumber : bingoNumbers) {
                if (handleBingoNumber(bingoBoards, bingoNumber)) {
                    break;
                }
            }
        }
    }

    private static boolean handleBingoNumber(List<BingoBoard> bingoBoards, int bingoNumber) {
        List<BingoBoard> winningBoards = bingoBoards.stream()
            .map(board -> board.mark(bingoNumber))
            .filter(BingoBoard::hasWon)
            .toList();
        if (winningBoards.size() == bingoBoards.size()) {
            OptionalInt winningScore = winningBoards
                .stream()
                .map(BingoBoard::getUnmarked)
                .mapToInt(IntStream::sum)
                .map(sum -> sum * bingoNumber)
                .max();
            winningScore.ifPresent(score -> System.out.printf("The score of the best board to win first is: %s", score));
            return winningScore.isPresent();
        }
        return false;
    }
}
