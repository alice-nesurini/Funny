package structure;


public class BoolExpr extends Expr {
    private final Boolean value;

    public BoolExpr(Boolean value) {
        this.value=value;
    }

    public Boolean getValue(){
        return this.value;
    }
}
