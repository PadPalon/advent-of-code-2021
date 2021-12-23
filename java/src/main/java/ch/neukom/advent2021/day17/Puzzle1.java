package ch.neukom.advent2021.day17;

import ch.neukom.advent2021.helper.InputResourceReader;

import java.io.IOException;

public class Puzzle1 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle1.class)) {
            int[] targetAreaPoints = Util.getTargetAreaPoints(reader.getFirstLine());
            int yMin = targetAreaPoints[2];
            int targetYSpeed = Math.abs(yMin) - 1;
            double highestPosition = (Math.pow(targetYSpeed, 2) + targetYSpeed) / 2;
            System.out.printf("The highest position the probe will reach is %s", highestPosition);
        }
    }
}
