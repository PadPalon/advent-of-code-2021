package ch.neukom.advent2021.day19;

import java.util.ArrayList;
import java.util.List;

public class Scanner {
    private final int id;
    private final List<Beacon> beacons = new ArrayList<>();

    public Scanner(int id) {
        this.id = id;
    }

    public void addBeacon(Beacon beacon) {
        this.beacons.add(beacon);
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    /**
     * oh so pretty
     */
    public List<Scanner> getOrientations() {
        return List.of(
                this,
                this.turn(),
                this.turn().turn(),
                this.turn().turn().turn(),
                roll(),
                roll().turn(),
                roll().turn().turn(),
                roll().turn().turn().turn(),
                roll().roll(),
                roll().roll().turn(),
                roll().roll().turn().turn(),
                roll().roll().turn().turn().turn(),
                roll().roll().roll(),
                roll().roll().roll().turn(),
                roll().roll().roll().turn().turn(),
                roll().roll().roll().turn().turn().turn(),
                turn().roll(),
                turn().roll().turn(),
                turn().roll().turn().turn(),
                turn().roll().turn().turn().turn(),
                turn().roll().roll().roll(),
                turn().roll().roll().roll().turn(),
                turn().roll().roll().roll().turn().turn(),
                turn().roll().roll().roll().turn().turn().turn()
        );
    }

    private Scanner roll() {
        Scanner rolledScanner = new Scanner(id);
        beacons.stream()
                .map(Beacon::roll)
                .forEach(rolledScanner::addBeacon);
        return rolledScanner;
    }

    private Scanner turn() {
        Scanner turnedScanner = new Scanner(id);
        beacons.stream()
                .map(Beacon::turn)
                .forEach(turnedScanner::addBeacon);
        return turnedScanner;
    }
}
