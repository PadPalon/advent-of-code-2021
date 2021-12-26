package ch.neukom.advent2021.day21;

import java.util.*;

public class Game {
    private final int winningScore;
    private final List<String> playerOrder;
    private final Map<String, Integer> playerPositions;
    private final Map<String, Integer> playerPoints;

    private int currentPlayer = 0;

    public Game(int winningScore) {
        this.winningScore = winningScore;
        this.playerOrder = new ArrayList<>();
        this.playerPositions = new HashMap<>();
        this.playerPoints = new HashMap<>();
    }

    private Game(Game game) {
        this.winningScore = game.winningScore;
        this.playerOrder = new ArrayList<>(game.playerOrder);
        this.playerPositions = new HashMap<>(game.playerPositions);
        this.playerPoints = new HashMap<>(game.playerPoints);
        this.currentPlayer = game.currentPlayer;
    }

    public void addPlayer(String playerName, int startingPosition) {
        this.playerOrder.add(playerName);
        this.playerPositions.put(playerName, startingPosition);
        this.playerPoints.put(playerName, 0);
    }

    public Optional<String> getWinningPlayer() {
        return playerPoints.entrySet()
                .stream()
                .filter(i -> i.getValue() >= winningScore)
                .map(Map.Entry::getKey)
                .findAny();
    }

    public Map<String, Integer> getPlayerPoints() {
        return Map.copyOf(playerPoints);
    }

    public void roll(int roll) {
        String player = playerOrder.get(this.currentPlayer);
        playerPositions.computeIfPresent(player, (p, pos) -> {
            int newPosition = (pos + roll) % 10;
            return newPosition == 0 ? 10 : newPosition;
        });
        playerPoints.computeIfPresent(player, (p, pos) -> pos + playerPositions.get(p));
        this.currentPlayer = (this.currentPlayer + 1) % playerOrder.size();
    }

    public Game splitOff(int roll) {
        Game game = new Game(this);
        game.roll(roll);
        return game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (currentPlayer != game.currentPlayer) return false;
        if (!playerPositions.equals(game.playerPositions)) return false;
        return playerPoints.equals(game.playerPoints);
    }

    @Override
    public int hashCode() {
        int result = playerPositions.hashCode();
        result = 31 * result + playerPoints.hashCode();
        result = 31 * result + currentPlayer;
        return result;
    }
}
