package ch.neukom.advent2021.day13;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.base.Splitter;

public class Page {
    public static final Pattern FOLD_PATTERN = Pattern.compile("fold along ([xy])=([0-9]+)");
    private static final Splitter COMMA_SPLITTER = Splitter.on(',').trimResults().omitEmptyStrings();

    private final int width;
    private final int height;
    private final Boolean[][] page;

    private int smallestXFold = Integer.MAX_VALUE;
    private int smallestYFold = Integer.MAX_VALUE;

    private Page(int width, int height, Boolean[][] page) {
        this.width = width;
        this.height = height;
        this.page = page;
    }

    public static Page build(Stream<String> input) {
        IntStream.Builder widthStream = IntStream.builder();
        IntStream.Builder heightStream = IntStream.builder();

        List<int[]> coordinates = input.takeWhile(line -> !line.isEmpty())
            .map(line -> COMMA_SPLITTER.splitToStream(line).mapToInt(Integer::parseInt).toArray())
            .peek(coord -> {
                widthStream.accept(coord[0]);
                heightStream.accept(coord[1]);
            })
            .toList();

        int width = widthStream.build().max().orElseThrow() + 1;
        int height = heightStream.build().max().orElseThrow() + 1;

        Boolean[][] page = coordinates.stream()
            .reduce(
                new Boolean[width][height],
                (array, coordinate) -> {
                    array[coordinate[0]][coordinate[1]] = true;
                    return array;
                },
                (left, right) -> {
                    Boolean[][] combined = new Boolean[width][height];
                    for (int x = 0; x < width; x++) {
                        for (int y = 0; y < height; y++) {
                            combined[x][y] = left[x][y] || right[x][y];
                        }
                    }
                    return combined;
                });

        return new Page(width, height, page);
    }

    public void fold(String direction, int foldPosition) {
        int xStart = 0;
        int yStart = 0;
        if (direction.equals("x")) {
            if (foldPosition < smallestXFold) {
                smallestXFold = foldPosition;
            }
            xStart = foldPosition + 1;
        } else if (direction.equals("y")) {
            if (foldPosition < smallestYFold) {
                smallestYFold = foldPosition;
            }
            yStart = foldPosition + 1;
        }
        for (int x = xStart; x < width; x++) {
            int newX = xStart != 0 ? 2 * foldPosition - x : x;
            for (int y = yStart; y < height; y++) {
                int newY = yStart != 0 ? 2 * foldPosition - y : y;
                if (hasDot(x, y)) {
                    page[newX][newY] = true;
                    page[x][y] = false;
                }
            }
        }
    }

    public int getDotCount() {
        int dotCount = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (hasDot(x, y)) {
                    dotCount++;
                }
            }
        }
        return dotCount;
    }

    public String formatPage() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < smallestYFold && y < height; y++) {
            for (int x = 0; x < smallestXFold && x < width; x++) {
                if (x % 5 == 0) {
                    builder.append(" |  ");
                }
                builder.append(hasDot(x, y) ? 'X' : ' ');
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private Boolean hasDot(int x, int y) {
        return page[x][y] != null && page[x][y];
    }
}
