package ch.neukom.advent2021.day12;

public class Util {
    private Util() {
    }

    public static boolean isUppercase(String string) {
        return string.chars().allMatch(Character::isUpperCase);
    }

    public static boolean isLowercase(String string) {
        return string.chars().noneMatch(Character::isUpperCase);
    }
}
