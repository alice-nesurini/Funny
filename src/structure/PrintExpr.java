package structure;

public class PrintExpr extends Expr{

    private final ExprSequence sequence;

    public PrintExpr(ExprSequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public <T> T getValue() {
        return null;
    }
}
