package ch.neukom.advent2021.day8;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

public class ConnectionMatcher {
    private final Map<Integer, Set<Character>> numberConnections = new HashMap<>();

    private ConnectionMatcher() {
    }

    public static ConnectionMatcher setup(List<Set<Character>> connections) {
        ConnectionMatcher matcher = new ConnectionMatcher();
        matcher.determineConnections(connections);
        return matcher;
    }

    public Integer findMatchingConnection(Set<Character> number) {
        return numberConnections.entrySet()
            .stream()
            .filter(entry -> Sets.symmetricDifference(number, entry.getValue()).isEmpty())
            .findAny()
            .map(Map.Entry::getKey)
            .orElseThrow();
    }

    private void determineConnections(List<Set<Character>> connections) {
        Set<Character> oneConnection = connections.stream().filter(c -> c.size() == 2).findAny().orElseThrow();
        numberConnections.put(1, oneConnection);
        Set<Character> sevenConnection = connections.stream().filter(c -> c.size() == 3).findAny().orElseThrow();
        numberConnections.put(7, sevenConnection);

        Character topLine = Sets.difference(sevenConnection, oneConnection).stream().findAny().orElseThrow();

        Set<Character> fourConnection = connections.stream().filter(c -> c.size() == 4).findAny().orElseThrow();
        numberConnections.put(4, fourConnection);

        Set<Character> nineConnection = findNineConnection(connections, fourConnection, topLine);
        numberConnections.put(9, nineConnection);

        Set<Character> eightConnection = connections.stream().filter(c -> c.size() == 7).findAny().orElseThrow();
        numberConnections.put(8, eightConnection);

        Character bottomLeftLine = Sets.difference(eightConnection, nineConnection).stream().findAny().orElseThrow();

        Set<Character> twoConnection = findTwoConnection(connections, bottomLeftLine);
        numberConnections.put(2, twoConnection);

        Character bottomLine = Sets.difference(nineConnection, fourConnection).stream().filter(c -> c != topLine).findAny().orElseThrow();
        Character topRightLine = Sets.intersection(oneConnection, twoConnection).stream().findAny().orElseThrow();
        Character middleLine = Sets.difference(twoConnection, Set.of(topLine, topRightLine, bottomLine, bottomLeftLine)).stream().findAny().orElseThrow();
        Character bottomRightLine = Sets.difference(sevenConnection, Set.of(topLine, topRightLine)).stream().findAny().orElseThrow();
        Character topLeftLine = Sets.difference(fourConnection, Set.of(topRightLine, middleLine, bottomRightLine)).stream().findAny().orElseThrow();

        numberConnections.put(0, findConnections(connections, 6, middleLine));
        numberConnections.put(3, findConnections(connections, 5, topLeftLine, bottomLeftLine));
        numberConnections.put(5, findConnections(connections, 5, topRightLine, bottomLeftLine));
        numberConnections.put(6, findConnections(connections, 6, topRightLine));
    }

    private Set<Character> findNineConnection(List<Set<Character>> connections, Set<Character> fourConnection, Character topLine) {
        return connections.stream()
            .filter(i -> i.size() == 6)
            .filter(i -> i.contains(topLine))
            .filter(i -> i.containsAll(fourConnection))
            .findAny()
            .orElseThrow();
    }

    private Set<Character> findTwoConnection(List<Set<Character>> connections, Character bottomLeftLine) {
        return connections.stream()
            .filter(i -> i.size() == 5)
            .filter(i -> i.contains(bottomLeftLine))
            .findAny()
            .orElseThrow();
    }

    private Set<Character> findConnections(List<Set<Character>> connections, int length, Character... excludedCharacters) {
        return connections.stream()
            .filter(i -> i.size() == length)
            .filter(i -> Arrays.stream(excludedCharacters).noneMatch(i::contains))
            .findAny()
            .orElseThrow();
    }
}
