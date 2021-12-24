package ch.neukom.advent2021.day18;

import ch.neukom.advent2021.helper.InputResourceReader;

import java.io.IOException;

public class Puzzle1 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            Long finalMagnitude = reader.readInput()
                    .map(SnailNumberReader::new)
                    .map(SnailNumberReader::parse)
                    .map(SnailNumber::reduce)
                    .reduce(SnailNumber::add)
                    .map(SnailNumber::magnitude)
                    .orElseThrow();
            System.out.printf("The final magnitude is %s", finalMagnitude);
        }
    }
}
