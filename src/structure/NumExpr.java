package structure;

import java.math.BigDecimal;

public class NumExpr extends Expr{

    private final BigDecimal value;

    public NumExpr(BigDecimal value) {
        this.value=value;
    }

    public BigDecimal getValue(){
        return this.value;
    }
}
