package ch.neukom.advent2021.day19;

import ch.neukom.advent2021.helper.InputResourceReader;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

/**
 * I struggled very hard with this one and have no idea how to neatly split it into two parts
 */
public class Puzzle {
    private static final Pattern SCANNER_PATTERN = Pattern.compile("--- scanner ([0-9]+) ---");
    private static final Splitter NEW_LINE_SPLITTER = Splitter.on('\n').trimResults().omitEmptyStrings();

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Puzzle.class)) {
            Deque<Scanner> scannersToHandle = loadScanners(reader);
            solve(scannersToHandle);
        }
    }

    private static Deque<Scanner> loadScanners(InputResourceReader reader) {
        String input = reader.readInput().collect(joining("\n"));
        String[] scannerStrings = input.split("\n\n");
        Deque<Scanner> scannersToHandle = new ArrayDeque<>();
        for (String scannerString : scannerStrings) {
            int firstNewLineIndex = scannerString.indexOf('\n');
            String scannerLine = scannerString.substring(0, firstNewLineIndex);
            Matcher scannerMatcher = SCANNER_PATTERN.matcher(scannerLine);
            Scanner scanner = createScanner(scannerMatcher);
            String beaconLines = scannerString.substring(firstNewLineIndex + 1);
            loadBeacons(scanner, beaconLines);
            scannersToHandle.push(scanner);
        }
        return scannersToHandle;
    }

    private static Scanner createScanner(Matcher scannerMatcher) {
        Scanner scanner;
        if (scannerMatcher.matches()) {
            scanner = new Scanner(Integer.parseInt(scannerMatcher.group(1)));
        } else {
            throw new IllegalStateException("Could not find scanner number");
        }
        return scanner;
    }

    private static void loadBeacons(Scanner scanner, String beaconLines) {
        NEW_LINE_SPLITTER.splitToStream(beaconLines)
                .map(line -> line.split(","))
                .map(Arrays::stream)
                .map(coords -> coords.mapToInt(Integer::parseInt))
                .map(IntStream::toArray)
                .map(coords -> new Beacon(coords[0], coords[1], coords[2]))
                .forEach(scanner::addBeacon);
    }

    private static Multiset.Entry<Vector3> calculateMostCommonDistance(Set<Beacon> knownBeacons, Scanner currentOrientation) {
        Multiset<Vector3> distances = HashMultiset.create();
        for (Beacon knownBeacon : knownBeacons) {
            for (Beacon currentBeacon : currentOrientation.getBeacons()) {
                distances.add(new Vector3(knownBeacon, currentBeacon));
            }
        }
        return distances.entrySet()
                .stream()
                .max(Comparator.comparing(Multiset.Entry::getCount))
                .orElseThrow();
    }

    private static void solve(Deque<Scanner> scannersToHandle) {
        Map<Scanner, Vector3> scannerDistanceToBase = positionScanners(scannersToHandle);
        calculateLongestDistance(scannerDistanceToBase);
    }

    private static Map<Scanner, Vector3> positionScanners(Deque<Scanner> scannersToHandle) {
        Map<Scanner, Vector3> scannerDistanceToBase = new HashMap<>();
        Scanner baseScanner = scannersToHandle.pop();
        scannerDistanceToBase.put(baseScanner, new Vector3(0, 0, 0));
        Set<Beacon> knownBeacons = new HashSet<>(baseScanner.getBeacons());
        while (!scannersToHandle.isEmpty()) {
            for (Scanner currentScanner : scannersToHandle) {
                for (Scanner currentOrientation : currentScanner.getOrientations()) {
                    Multiset.Entry<Vector3> mostCommonDistance = calculateMostCommonDistance(knownBeacons, currentOrientation);
                    if (mostCommonDistance.getCount() >= 12) {
                        currentOrientation.getBeacons()
                                .stream()
                                .map(beacon -> beacon.move(mostCommonDistance.getElement()))
                                .forEach(knownBeacons::add);
                        scannerDistanceToBase.put(currentScanner, mostCommonDistance.getElement());
                    }
                }
            }
            scannersToHandle.removeAll(scannerDistanceToBase.keySet());
        }
        System.out.printf("There are a total of %s unique beacons%n", knownBeacons.size());
        return scannerDistanceToBase;
    }

    private static void calculateLongestDistance(Map<Scanner, Vector3> scannerDistanceToBase) {
        List<Vector3> distances = List.copyOf(scannerDistanceToBase.values());
        // this compares scanners with themselves, but those are always 0 distance away from each other
        int longestDistance = Lists.cartesianProduct(distances, distances)
                .stream()
                .mapToInt(d -> d.get(0).distanceTo(d.get(1)))
                .max()
                .orElseThrow();
        System.out.printf("The longest distance between to scanners is %s%n", longestDistance);
    }
}
