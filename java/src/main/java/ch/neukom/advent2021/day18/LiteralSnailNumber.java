package ch.neukom.advent2021.day18;

public class LiteralSnailNumber implements SnailNumber {
    private PairSnailNumber parent;
    private int value;

    public LiteralSnailNumber(int value) {
        this.value = value;
    }

    public void split() {
        int leftValue = this.value / 2;
        int rightValue = value - leftValue;
        parent.replace(this, new PairSnailNumber(new LiteralSnailNumber(leftValue), new LiteralSnailNumber(rightValue)));
    }

    public void add(LiteralSnailNumber toAdd) {
        this.value += toAdd.magnitude();
    }

    @Override
    public void setParent(PairSnailNumber parent) {
        this.parent = parent;
    }

    @Override
    public SnailNumber reduce() {
        return this;
    }

    @Override
    public long magnitude() {
        return value;
    }

    @Override
    public SnailNumber copy() {
        return new LiteralSnailNumber(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
