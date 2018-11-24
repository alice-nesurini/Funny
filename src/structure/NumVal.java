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

    public NumVal checkNum(){
        return this;
    }

}
