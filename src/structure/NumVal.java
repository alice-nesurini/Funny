package structure;

import java.math.BigDecimal;

public class NumVal extends Val {

    private final BigDecimal value;

    public NumVal(BigDecimal value) {
        this.value=value;
    }

    public BigDecimal getValue(){
        return this.value;
    }
}
