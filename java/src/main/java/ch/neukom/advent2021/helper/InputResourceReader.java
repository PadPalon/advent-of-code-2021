package ch.neukom.advent2021.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class InputResourceReader implements AutoCloseable {
    private final Class<?> clazz;

    private BufferedReader reader;

    public InputResourceReader(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Stream<String> readInput() {
        return readInput("input");
    }

    public Stream<String> readInput(String filename) {
        InputStream is = clazz.getResourceAsStream(filename);
        if (is == null) {
            System.out.println("Could not read input");
            return Stream.empty();
        }
        InputStreamReader isr = new InputStreamReader(is);
        reader = new BufferedReader(isr);
        return reader.lines();
    }

    public String getFirstLine() {
        return readInput().findFirst().orElseThrow();
    }

    public long getLineCount() {
        return readInput().count();
    }

    @Override
    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}
