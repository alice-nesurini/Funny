package structure;

import java.math.BigDecimal;

public class NumVal extends Val {

    private BigDecimal value;

    public NumVal(BigDecimal value) {
        this.value=value;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public NumVal checkNum(){
        return this;
    }

    @Override
    public String toString() {
        return value.toString();
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
        // include 0
        return value.compareTo(val.checkNum().value) == 1 || value.compareTo(val.checkNum().value) == 0;
    }

    @Override
    public Val subtract(Val val) throws InterpreterException {
        this.value=value.subtract(val.checkNum().getValue());
        return this;
    }

    @Override
    public Val add(Val val) throws InterpreterException {
        this.value=value.add(val.checkNum().getValue());
        return this;
    }

    @Override
    public Val module(Val val) throws InterpreterException {
        this.value=value.remainder(val.checkNum().getValue());
        return this;
    }

    @Override
    public Val star(Val val) throws InterpreterException {
        this.value=value.multiply(val.checkNum().getValue());
        return this;
    }

    public Val negate() {
        value=value.negate();
        return this;
    }

    public Val plus() {
        value=value.plus();
        return this;
    }
}
