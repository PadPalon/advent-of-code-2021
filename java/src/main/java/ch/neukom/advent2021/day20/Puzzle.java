package ch.neukom.advent2021.day20;

import ch.neukom.advent2021.helper.InputResourceReader;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;

/**
 * seems like most inputs have a # at the start of the algorithm, so I assume this as given and must handle the flickering
 * <p>
 * also, this is an unholy mess that wrecked my brain
 */
public class Puzzle {

    public static final int ENHANCEMENT_ITERATIONS = 50;

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle.class)) {
            String[] inputs = reader.readInput().collect(collectingAndThen(joining("\n"), input -> input.split("\n\n")));
            Pixel startingPixel = loadInitialImage(inputs);

            String enhancement = inputs[0];
            Pixel current = startingPixel;
            for (int i = 0; i < ENHANCEMENT_ITERATIONS; i++) {
                current = runEnhancement(current, enhancement, i + 1);
            }

            System.out.printf("There are %s lit pixels", findLitPixels(current));
        }
    }

    private static Pixel loadInitialImage(String[] inputs) {
        String inputImage = inputs[1];
        String[] imageLines = inputImage.split("\n");
        Pixel[][] parsedPixels = new Pixel[imageLines[0].length()][imageLines.length];
        Pixel startingPixel = null;
        for (int y = 0; y < imageLines.length; y++) {
            String imageLine = imageLines[y];
            char[] lineChars = imageLine.toCharArray();
            for (int x = 0; x < lineChars.length; x++) {
                Pixel pixel = new Pixel(x, y, isLit(lineChars[x]));
                parsedPixels[x][y] = pixel;
                if (startingPixel == null) {
                    startingPixel = pixel;
                }
                if (x > 0) {
                    pixel.setLeftNeighbour(parsedPixels[x - 1][y]);
                }
                if (y > 0) {
                    pixel.setTopNeighbour(parsedPixels[x][y - 1]);
                }
            }
        }
        assert startingPixel != null;
        return startingPixel;
    }

    private static Pixel runEnhancement(Pixel startingPixel, String enhancement, int runNumber) {
        Pixel currentPixel = addBorders(startingPixel, runNumber);
        Pixel lastLeftPixel = null;
        Pixel lastTopPixel = null;
        Pixel newStartingPixel = null;
        boolean connectLastTop = false;
        Pixel nextPixel;
        while (currentPixel.getRight().isPresent() || currentPixel.getBottom().isPresent()) {
            nextPixel = enhanceAndConnectPixel(
                    enhancement,
                    runNumber,
                    currentPixel,
                    connectLastTop,
                    connectLastTop ? lastTopPixel : lastLeftPixel
            );
            if (newStartingPixel == null) {
                newStartingPixel = nextPixel;
            }
            lastLeftPixel = nextPixel;
            if (currentPixel.getLeft().isEmpty()) {
                lastTopPixel = nextPixel;
            }

            Optional<Pixel> nextRightPixel = currentPixel.getRight();
            if (nextRightPixel.isPresent()) {
                connectLastTop = false;
                currentPixel = nextRightPixel.get();
            } else {
                connectLastTop = true;
                currentPixel = findNextRowStart(currentPixel);
            }
        }
        // last pixel is not done yet
        enhanceAndConnectPixel(enhancement, runNumber, currentPixel, false, lastLeftPixel);

        assert newStartingPixel != null;
        return newStartingPixel;
    }

    private static Pixel findNextRowStart(Pixel currentPixel) {
        Optional<Pixel> nextBottomPixel = currentPixel.getBottom();
        if (nextBottomPixel.isPresent()) {
            Pixel temp = nextBottomPixel.get();
            while (temp.getLeft().isPresent()) {
                temp = temp.getLeft().orElseThrow();
            }
            currentPixel = temp;
        }
        return currentPixel;
    }

    private static Pixel addBorders(Pixel startingPixel, int runNumber) {
        Pixel newTopLeft = new Pixel(startingPixel.getX() - 1, startingPixel.getY() - 1, runNumber % 2 == 0);
        Pixel top = createFirstTopBorder(startingPixel, runNumber, newTopLeft);
        Pixel left = createFirstLeftBorder(startingPixel, runNumber, newTopLeft);
        Pixel lastTop = buildTopBorder(startingPixel, runNumber, top);
        Pixel lastLeft = buildLeftBorder(startingPixel, runNumber, left);
        buildBottomBorder(runNumber, lastLeft);
        buildRightBorder(runNumber, lastTop);
        return newTopLeft;
    }

    private static Pixel createFirstTopBorder(Pixel startingPixel, int runNumber, Pixel newTopLeft) {
        Pixel top = new Pixel(startingPixel.getX(), startingPixel.getY() - 1, runNumber % 2 == 0);
        startingPixel.setTopNeighbour(top);
        top.setLeftNeighbour(newTopLeft);
        return top;
    }

    private static Pixel createFirstLeftBorder(Pixel startingPixel, int runNumber, Pixel newTopLeft) {
        Pixel left = new Pixel(startingPixel.getX() - 1, startingPixel.getY(), runNumber % 2 == 0);
        startingPixel.setLeftNeighbour(left);
        left.setTopNeighbour(newTopLeft);
        return left;
    }

    private static Pixel buildTopBorder(Pixel startingPixel, int runNumber, Pixel top) {
        Optional<Pixel> currentOptional = startingPixel.getRight();
        Pixel lastTop = top;
        while (currentOptional.isPresent()) {
            Pixel current = currentOptional.orElseThrow();
            Pixel newTop = new Pixel(current.getX(), current.getY() - 1, runNumber % 2 == 0);
            newTop.setLeftNeighbour(lastTop);
            current.setTopNeighbour(newTop);
            lastTop = newTop;
            currentOptional = current.getRight();
        }
        return lastTop;
    }

    private static Pixel buildLeftBorder(Pixel startingPixel, int runNumber, Pixel left) {
        Optional<Pixel> currentOptional = startingPixel.getBottom();
        Pixel lastLeft = left;
        while (currentOptional.isPresent()) {
            Pixel current = currentOptional.orElseThrow();
            Pixel newLeft = new Pixel(current.getX() - 1, current.getY(), runNumber % 2 == 0);
            newLeft.setTopNeighbour(lastLeft);
            current.setLeftNeighbour(newLeft);
            lastLeft = newLeft;
            currentOptional = current.getBottom();
        }
        return lastLeft;
    }

    private static void buildBottomBorder(int runNumber, Pixel lastLeft) {
        Pixel newBottomLeft = new Pixel(lastLeft.getX(), lastLeft.getY() + 1, runNumber % 2 == 0);
        newBottomLeft.setTopNeighbour(lastLeft);
        Optional<Pixel> currentOptional = lastLeft.getRight();
        lastLeft = newBottomLeft;
        while (currentOptional.isPresent()) {
            Pixel current = currentOptional.orElseThrow();
            Pixel newBottom = new Pixel(current.getX(), current.getY() + 1, runNumber % 2 == 0);
            newBottom.setTopNeighbour(current);
            newBottom.setLeftNeighbour(lastLeft);
            lastLeft = newBottom;
            currentOptional = current.getRight();
        }
    }

    private static void buildRightBorder(int runNumber, Pixel lastTop) {
        Pixel newTopRight = new Pixel(lastTop.getX() + 1, lastTop.getY(), runNumber % 2 == 0);
        newTopRight.setLeftNeighbour(lastTop);
        Optional<Pixel> currentOptional = lastTop.getBottom();
        lastTop = newTopRight;
        while (currentOptional.isPresent()) {
            Pixel current = currentOptional.orElseThrow();
            Pixel newRight = new Pixel(current.getX() + 1, current.getY(), runNumber % 2 == 0);
            newRight.setTopNeighbour(lastTop);
            newRight.setLeftNeighbour(current);
            lastTop = newRight;
            currentOptional = current.getBottom();
        }
    }

    private static Pixel enhanceAndConnectPixel(String enhancement,
                                                int runNumber,
                                                Pixel input,
                                                boolean connectLastTop,
                                                @Nullable Pixel lastPixel) {
        Pixel nextPixel = enhancePixel(enhancement, runNumber, input);

        if (lastPixel != null) {
            if (connectLastTop) {
                nextPixel.setTopNeighbour(lastPixel);
            } else {
                nextPixel.setLeftNeighbour(lastPixel);
                lastPixel.getTop().flatMap(Pixel::getRight).ifPresent(nextPixel::setTopNeighbour);
            }
        }

        return nextPixel;
    }

    private static Pixel enhancePixel(String enhancement, int runNumber, Pixel input) {
        Stream.Builder<Pixel> streamBuilder = Stream.builder();
        input.getTop().flatMap(Pixel::getLeft)
                .or(() -> Optional.of(new Pixel(input.getX() - 1, input.getY() - 1, runNumber % 2 == 0)))
                .ifPresent(streamBuilder);
        input.getTop()
                .or(() -> Optional.of(new Pixel(input.getX(), input.getY() - 1, runNumber % 2 == 0)))
                .ifPresent(streamBuilder);
        input.getTop().flatMap(Pixel::getRight)
                .or(() -> Optional.of(new Pixel(input.getX() + 1, input.getY() - 1, runNumber % 2 == 0)))
                .ifPresent(streamBuilder);
        input.getLeft()
                .or(() -> Optional.of(new Pixel(input.getX() - 1, input.getY(), runNumber % 2 == 0)))
                .ifPresent(streamBuilder);
        streamBuilder.accept(input);
        input.getRight()
                .or(() -> Optional.of(new Pixel(input.getX() + 1, input.getY(), runNumber % 2 == 0)))
                .ifPresent(streamBuilder);
        input.getBottom().flatMap(Pixel::getLeft)
                .or(() -> Optional.of(new Pixel(input.getX() - 1, input.getY() + 1, runNumber % 2 == 0)))
                .ifPresent(streamBuilder);
        input.getBottom()
                .or(() -> Optional.of(new Pixel(input.getX(), input.getY() + 1, runNumber % 2 == 0)))
                .ifPresent(streamBuilder);
        input.getBottom().flatMap(Pixel::getRight)
                .or(() -> Optional.of(new Pixel(input.getX() + 1, input.getY() + 1, runNumber % 2 == 0)))
                .ifPresent(streamBuilder);
        return streamBuilder.build()
                .map(Puzzle::mapToBinary)
                .collect(collectingAndThen(joining(), Optional::of))
                .map(n -> Integer.parseInt(n, 2))
                .map(enhancement::charAt)
                .map(Puzzle::isLit)
                .map(lit -> new Pixel(input.getX(), input.getY(), lit))
                .orElseThrow();
    }

    private static boolean isLit(Character newPixel) {
        return newPixel == '#';
    }

    private static String mapToBinary(Pixel pixel) {
        return pixel.isLit() ? "1" : "0";
    }

    private static int findLitPixels(Pixel startingPixel) {
        int counter = 0;
        Optional<Pixel> currentRow = Optional.of(startingPixel);
        while (currentRow.isPresent()) {
            Optional<Pixel> currentColumn = currentRow;
            while (currentColumn.isPresent()) {
                if (currentColumn.get().isLit()) {
                    counter++;
                }
                currentColumn = currentColumn.flatMap(Pixel::getRight);
            }
            currentRow = currentRow.flatMap(Pixel::getBottom);
        }
        return counter;
    }

    /**
     * used for debugging
     */
    @SuppressWarnings("unused")
    private static String formatImage(Pixel startingPixel) {
        StringBuilder builder = new StringBuilder();
        Optional<Pixel> currentRow = Optional.of(startingPixel);
        while (currentRow.isPresent()) {
            Optional<Pixel> currentColumn = currentRow;
            while (currentColumn.isPresent()) {
                builder.append(currentColumn.get().isLit() ? '#' : '.');
                currentColumn = currentColumn.flatMap(Pixel::getRight);
            }
            builder.append('\n');
            currentRow = currentRow.flatMap(Pixel::getBottom);
        }
        return builder.toString();
    }
}
