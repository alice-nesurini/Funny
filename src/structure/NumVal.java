package structure;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumVal extends Val {

    private final BigDecimal value;

    public NumVal(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public NumVal checkNum() {
        return this;
    }

    @Override
    public boolean less(Val val) throws InterpreterException {
        return value.compareTo(val.checkNum().value) == -1;
    }

    @Override
    public boolean greater(Val val) throws InterpreterException {
        return value.compareTo(val.checkNum().value) == 1;
    }

    @Override
    public boolean lessEquals(Val val) throws InterpreterException {
        // include 0
        return value.compareTo(val.checkNum().value) == -1 || value.compareTo(val.checkNum().value) == 0;
    }

    @Override
    public boolean greaterEquals(Val val) throws InterpreterException {
        return value.compareTo(val.checkNum().value) == 1 || value.compareTo(val.checkNum().value) == 0;
    }

    @Override
    public Val subtract(Val val) throws InterpreterException {
        return new NumVal(value.subtract(val.checkNum().getValue()));
    }

    @Override
    public Val add(Val val) throws InterpreterException {
        return new NumVal(value.add(val.checkNum().getValue()));
    }

    @Override
    public Val module(Val val) throws InterpreterException {
        return new NumVal(value.remainder(val.checkNum().getValue()));
    }

    @Override
    public Val star(Val val) throws InterpreterException {
        return new NumVal(value.multiply(val.checkNum().getValue()));
    }

    @Override
    public Val divide(Val val) throws InterpreterException {
        NumVal numVal;
        try {
            numVal = new NumVal(value.divide(val.checkNum().getValue()));
        } catch (ArithmeticException e) {
            numVal = new NumVal(value.divide(val.checkNum().getValue(), 100, RoundingMode.HALF_EVEN));
        }
        return numVal;
    }

    public Val negate() {
        return new NumVal(value.negate());
    }

    public Val plus() {
        return new NumVal(value.plus());
    }

    @Override
    public Boolean comparison(Val val) throws InterpreterException {
        return this.value.compareTo(val.checkNum().value) == 0;
    }

    @Override
    public Boolean difference(Val val) throws InterpreterException {
        return this.value.compareTo(val.checkNum().value) != 0;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
