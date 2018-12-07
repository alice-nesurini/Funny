package structure;

import java.math.BigDecimal;

public class NumVal extends Val {

    private final BigDecimal value;

    //TODO: add operation like eq, minus

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
}
