package ch.neukom.advent2021.day18;

public class SnailNumberReader {
    private final String input;

    public SnailNumberReader(String input) {
        this.input = input;
    }

    public SnailNumber parse() {
        int currentPos = 1;
        int leftStart = 1;
        int leftEnd = -1;
        int rightStart = -1;
        int parenCount = 1;
        while (parenCount >= 1) {
            if (input.charAt(currentPos) == '[') {
                parenCount++;
            }
            if (input.charAt(currentPos) == ']') {
                parenCount--;
            }
            if (parenCount == 1 && input.charAt(currentPos) == ',') {
                leftEnd = currentPos;
                rightStart = currentPos + 1;
            }
            currentPos++;
        }
        int rightEnd = currentPos - 1;
        return new PairSnailNumber(
                parseSubstring(leftStart, leftEnd),
                parseSubstring(rightStart, rightEnd)
        );
    }

    private SnailNumber parseSubstring(int start, int end) {
        if (end - start == 1) {
            return new LiteralSnailNumber(Integer.parseInt(input.substring(start, end)));
        } else {
            return new SnailNumberReader(input.substring(start, end)).parse();
        }
    }
}
