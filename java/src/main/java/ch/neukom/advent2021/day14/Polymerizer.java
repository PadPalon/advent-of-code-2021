package ch.neukom.advent2021.day14;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Polymerizer {
    private final List<String> pairs = new ArrayList<>();

    public Polymerizer(String input) {
        for (int i = 0; i < input.length() - 1; i++) {
            pairs.add(input.substring(i, i + 2));
        }
    }

    public Polymerizer runInsertions(Map<String, String> insertions) {
        return pairs.stream()
            .map(pair -> insertions.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(pair))
                .findAny()
                .map(Map.Entry::getValue)
                .orElse(pair)
            )
            .reduce((left, right) -> left + right.substring(1))
            .map(Polymerizer::new)
            .orElseThrow();
    }

    public String getOutput() {
        return pairs.stream()
            .reduce((left, right) -> left + right.substring(1))
            .orElseThrow();
    }
}
