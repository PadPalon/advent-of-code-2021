package ch.neukom.advent2021.day1;

public class Util {
    private Util() {
    }

    protected static int getIncreaseCount(int[] windowSums) {
        Integer lastSum = null;
        int increaseCount = 0;
        for (int sum : windowSums) {
            if (lastSum != null) {
                if (sum > lastSum) {
                    increaseCount++;
                }
            }
            lastSum = sum;
        }
        return increaseCount;
    }
}
