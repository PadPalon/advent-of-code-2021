package ch.neukom.advent2021.day18;

import javax.annotation.Nullable;

public class PairSnailNumber implements SnailNumber {
    private SnailNumber left;
    private SnailNumber right;
    @Nullable
    private PairSnailNumber parent;

    public PairSnailNumber(SnailNumber left, SnailNumber right) {
        this.left = left;
        this.left.setParent(this);
        this.right = right;
        this.right.setParent(this);
    }

    public void explode() {
        if (parent == null) {
            return;
        }

        LiteralSnailNumber nextLeft = parent.findNextLeft(this);
        if (nextLeft != null) {
            nextLeft.add((LiteralSnailNumber) left);
        }
        LiteralSnailNumber nextRight = parent.findNextRight(this);
        if (nextRight != null) {
            nextRight.add((LiteralSnailNumber) right);
        }

        parent.replace(this, new LiteralSnailNumber(0));
    }

    public void replace(SnailNumber child, SnailNumber replacement) {
        if (left.equals(child)) {
            left = replacement;
            left.setParent(this);
        }
        if (right.equals(child)) {
            right = replacement;
            right.setParent(this);
        }
    }

    @Override
    public long magnitude() {
        return 3 * left.magnitude() + 2 * right.magnitude();
    }

    @Override
    public SnailNumber copy() {
        return new PairSnailNumber(left.copy(), right.copy());
    }

    @Override
    public void setParent(@Nullable PairSnailNumber parent) {
        this.parent = parent;
    }

    @Override
    public SnailNumber reduce() {
        boolean runReduce = true;
        while (runReduce) {
            runReduce = explodeReduce();
            if (!runReduce) {
                runReduce = splitReduce();
            }
        }
        return this;
    }

    @Nullable
    private LiteralSnailNumber findNextLeft(SnailNumber source) {
        if (source.equals(right)) {
            if (left instanceof LiteralSnailNumber literal) {
                return literal;
            } else if (left instanceof PairSnailNumber pair) {
                return pair.resolveRight();
            }
        } else if (parent != null) {
            return parent.findNextLeft(this);
        }
        return null;
    }

    @Nullable
    private LiteralSnailNumber findNextRight(SnailNumber source) {
        if (source.equals(left)) {
            if (right instanceof LiteralSnailNumber literal) {
                return literal;
            } else if (right instanceof PairSnailNumber pair) {
                return pair.resolveLeft();
            }
        } else if (parent != null) {
            return parent.findNextRight(this);
        }
        return null;
    }

    private LiteralSnailNumber resolveLeft() {
        if (left instanceof PairSnailNumber pair) {
            return pair.resolveLeft();
        } else {
            return (LiteralSnailNumber) left;
        }
    }

    private LiteralSnailNumber resolveRight() {
        if (right instanceof PairSnailNumber pair) {
            return pair.resolveRight();
        } else {
            return (LiteralSnailNumber) right;
        }
    }

    private boolean explodeReduce() {
        if (level() == 4) {
            explode();
            return true;
        }
        return left instanceof PairSnailNumber pairL && pairL.explodeReduce()
                || right instanceof PairSnailNumber pairR && pairR.explodeReduce();
    }

    private boolean splitReduce() {
        return splitReduce(left) || splitReduce(right);
    }

    private boolean splitReduce(SnailNumber part) {
        if (part instanceof LiteralSnailNumber literal && literal.magnitude() >= 10) {
            literal.split();
            return true;
        }
        return part instanceof PairSnailNumber pair && pair.splitReduce();
    }

    private int level() {
        if (parent != null) {
            return parent.level() + 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return String.format("[(%s) %s,%s]", level(), left.toString(), right.toString());
    }
}
