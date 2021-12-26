package ch.neukom.advent2021.day21;

import ch.neukom.advent2021.helper.InputResourceReader;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle1 {
    private static final Pattern STARTING_POSITION_MATCHER = Pattern.compile("Player ([0-9]+) starting position: ([0-9]*)");

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            Game game = new Game(1000);
            reader.readInput()
                    .map(STARTING_POSITION_MATCHER::matcher)
                    .filter(Matcher::find)
                    .forEach(matcher -> game.addPlayer(matcher.group(1), Integer.parseInt(matcher.group(2))));

            int rollCount = playGame(game);
            int losingPlayersScore = game.getPlayerPoints()
                    .values()
                    .stream()
                    .mapToInt(i -> i)
                    .sorted()
                    .findFirst()
                    .orElseThrow();

            System.out.printf("%nThe losing players score multiplied with the number of times the dice was rolled equals %s%n", losingPlayersScore * rollCount);
        }
    }

    private static int playGame(Game game) {
        int lastRoll = 0;
        int rollCount = 0;
        while (game.getWinningPlayer().isEmpty()) {
            int currentRoll = ++lastRoll;
            currentRoll += ++lastRoll;
            currentRoll += ++lastRoll;
            rollCount += 3;
            game.roll(currentRoll);
        }
        return rollCount;
    }
}
