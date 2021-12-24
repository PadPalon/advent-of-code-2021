package ch.neukom.advent2021.day18;

public interface SnailNumber {
    void setParent(PairSnailNumber parent);

    default SnailNumber add(SnailNumber toAdd) {
        return new PairSnailNumber(this.copy(), toAdd.copy()).reduce();
    }

    SnailNumber reduce();

    long magnitude();

    SnailNumber copy();
}
