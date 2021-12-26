package ch.neukom.advent2021.day21;

import ch.neukom.advent2021.helper.InputResourceReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class Puzzle2 {
    private static final Pattern STARTING_POSITION_MATCHER = Pattern.compile("Player ([0-9]+) starting position: ([0-9]*)");
    private static final GameCache CACHE = new GameCache();
    private static final Map<Integer, Integer> POSSIBLE_ROLLS = IntStream.range(1, 4)
            .flatMap(first -> IntStream.range(1, 4)
                    .flatMap(second -> IntStream.range(1, 4)
                            .map(third -> first + second + third)))
            .boxed()
            .collect(groupingBy(roll -> roll))
            .entrySet()
            .stream()
            .collect(toMap(Map.Entry::getKey, group -> group.getValue().size()));

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            Game initialGame = new Game(21);
            reader.readInput()
                    .map(STARTING_POSITION_MATCHER::matcher)
                    .filter(Matcher::find)
                    .forEach(matcher -> initialGame.addPlayer(matcher.group(1), Integer.parseInt(matcher.group(2))));

            Map<String, Long> winningUniverses = getGameOutcomes(initialGame);

            long higherWinCount = winningUniverses.values()
                    .stream()
                    .mapToLong(l -> l)
                    .max()
                    .orElseThrow();

            System.out.printf("%nOne player wins in %s universes%n", higherWinCount);
        }
    }

    private static Map<String, Long> getGameOutcomes(Game game) {
        Map<String, Long> result = CACHE.getResult(game);
        if (result == null) {
            result = calculateGameOutcomes(game);
        }
        return result;
    }

    private static Map<String, Long> calculateGameOutcomes(Game game) {
        Map<String, Long> result = new HashMap<>();
        for (Map.Entry<Integer, Integer> possibleRoll : POSSIBLE_ROLLS.entrySet()) {
            Map<String, Long> winningPlayers = getRollOutcomes(game, possibleRoll.getKey());
            for (Map.Entry<String, Long> winEntry : winningPlayers.entrySet()) {
                String player = winEntry.getKey();
                Long wins = winEntry.getValue() * possibleRoll.getValue();
                result.compute(player, (p, previous) -> {
                    if (previous == null) {
                        return wins;
                    } else {
                        return previous + wins;
                    }
                });
            }
        }
        CACHE.putResult(game, result);
        return result;
    }

    private static Map<String, Long> getRollOutcomes(Game game, Integer roll) {
        Map<String, Long> winningPlayers = CACHE.getRoll(game, roll);
        if (winningPlayers == null) {
            winningPlayers = calculateRollOutcomes(game, roll);
        }
        return winningPlayers;
    }

    private static Map<String, Long> calculateRollOutcomes(Game game, Integer roll) {
        Map<String, Long> winningPlayers;
        Game newGame = game.splitOff(roll);
        Optional<String> winningPlayer = newGame.getWinningPlayer();
        if (winningPlayer.isPresent()) {
            winningPlayers = new HashMap<>();
            winningPlayers.put(winningPlayer.get(), 1L);
        } else {
            winningPlayers = getGameOutcomes(newGame);
        }
        CACHE.putRoll(game, roll, winningPlayers);
        return winningPlayers;
    }
}
