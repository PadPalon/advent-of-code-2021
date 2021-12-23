package ch.neukom.advent2021.day16;

import ch.neukom.advent2021.helper.CharStream;
import com.google.common.base.Strings;

import java.util.ArrayDeque;
import java.util.Deque;

import static java.util.stream.Collectors.toCollection;

public class Util {
    private Util() {
    }

    public static Deque<Character> readBits(String bitString) {
        return CharStream.of(bitString)
                .map(Object::toString)
                .map(Util::parseHex)
                .map(Integer::toBinaryString)
                .map(Util::padZeroesLeft)
                .flatMap(CharStream::of)
                .collect(toCollection(ArrayDeque::new));
    }

    private static int parseHex(String hexString) {
        return Integer.parseInt(hexString, 16);
    }

    private static String padZeroesLeft(String string) {
        return Strings.padStart(string, 4, '0');
    }
}
