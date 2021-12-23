package ch.neukom.advent2021.day16;

import java.util.List;
import java.util.OptionalLong;
import java.util.function.Function;

public enum PacketType {
    SUM(subs -> subs.stream().mapToLong(i -> i).reduce(Math::addExact)),
    PRODUCT(subs -> subs.stream().mapToLong(i -> i).reduce(Math::multiplyExact)),
    MIN(subs -> subs.stream().mapToLong(i -> i).min()),
    MAX(subs -> subs.stream().mapToLong(i -> i).max()),
    LITERAL(subs -> OptionalLong.empty()),
    GT(subs -> subs.get(0) > subs.get(1) ? OptionalLong.of(1) : OptionalLong.of(0)),
    LT(subs -> subs.get(0) < subs.get(1) ? OptionalLong.of(1) : OptionalLong.of(0)),
    EQ(subs -> subs.get(0).equals(subs.get(1)) ? OptionalLong.of(1) : OptionalLong.of(0));

    private final Function<List<Long>, OptionalLong> operator;

    PacketType(Function<List<Long>, OptionalLong> operator) {
        this.operator = operator;
    }

    public Function<List<Long>, OptionalLong> getOperator() {
        return operator;
    }
}
