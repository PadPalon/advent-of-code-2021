package ch.neukom.advent2021.day12;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;

public class CaveGraph {
    private final Multimap<String, String> connections = ArrayListMultimap.create();

    private BiPredicate<List<String>, String> cavePredicate = (currentPath, next) -> true;

    public Set<List<String>> getUniquePaths(String start, String end) {
        return getPaths(List.of(start), end);
    }

    public Set<List<String>> getPaths(List<String> currentPath, String end) {
        String currentPosition = currentPath.get(currentPath.size() - 1);
        if (currentPosition.equals(end)) {
            return Set.of(currentPath);
        } else {
            return connections.get(currentPosition)
                .stream()
                .filter(next -> cavePredicate.test(currentPath, next))
                .map(next -> copyPathAndAppend(currentPath, next))
                .map(newPath -> getPaths(newPath, end))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        }
    }

    private void addConnection(String from, String to) {
        this.connections.put(from, to);
        this.connections.put(to, from);
    }

    private void setCavePredicate(BiPredicate<List<String>, String> cavePredicate) {
        this.cavePredicate = cavePredicate;
    }

    private ImmutableList<String> copyPathAndAppend(List<String> currentPath, String c) {
        return ImmutableList.<String>builder().addAll(currentPath).add(c).build();
    }

    public static class Builder {
        private final static Pattern CONNECTION_PATTERN = Pattern.compile("([a-zA-Z]*)-([a-zA-Z]*)");

        private final CaveGraph graph = new CaveGraph();

        public Builder withCavePredicate(BiPredicate<List<String>, String> cavePredicate) {
            graph.setCavePredicate(cavePredicate);
            return this;
        }

        public Builder addConnection(String connection) {
            Optional.of(connection)
                .map(CONNECTION_PATTERN::matcher)
                .filter(Matcher::matches)
                .ifPresent(matcher -> {
                    String from = matcher.group(1);
                    String to = matcher.group(2);
                    graph.addConnection(from, to);
                });
            return this;
        }

        public CaveGraph build() {
            return graph;
        }
    }
}
