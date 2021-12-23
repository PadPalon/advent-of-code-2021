package ch.neukom.advent2021.day17;

import ch.neukom.advent2021.helper.InputResourceReader;

import java.io.IOException;

public class Puzzle2 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle2.class)) {
            int[] targetAreaPoints = Util.getTargetAreaPoints(reader.getFirstLine());
            int xMin = targetAreaPoints[0];
            int xMax = targetAreaPoints[1];
            int yMin = targetAreaPoints[2];
            int yMax = targetAreaPoints[3];

            int minXSpeed = calculateXSpeed(xMin);

            int maxYSpeedParabolic = Math.abs(yMin) - 1;

            int velocityCounter = 0;
            // not optimized, I'm sure there's area we could exclude
            for (int x = minXSpeed; x <= xMax; x++) { // any speed below minXSpeed will never reach, any speed above xMax will always overshoot
                for (int y = yMin; y <= maxYSpeedParabolic; y++) { // any speed outside yMin and maxYSpeedParabolic will always overshoot
                    if (simulateShot(x, y, xMin, xMax, yMin, yMax)) {
                        velocityCounter++;
                    }
                }
            }
            System.out.printf("%nThere are %s different velocities that reach the target area", velocityCounter);
        }
    }

    private static int calculateXSpeed(int x) {
        int xSpeed = 0;
        int xStep = 1;
        while (x > 0) {
            x -= xStep;
            xSpeed++;
            xStep++;
        }
        return xSpeed;
    }

    private static boolean simulateShot(int initialX, int initialY, int xMin, int xMax, int yMin, int yMax) {
        int currentX = 0;
        int currentY = 0;
        while (currentX <= xMax && currentY >= yMin) {
            currentX += initialX;
            currentY += initialY;
            if (initialX > 0) {
                initialX--;
            }
            initialY--;

            if (currentX >= xMin && currentX <= xMax && currentY >= yMin && currentY <= yMax) {
                return true;
            }
        }
        return false;
    }
}
