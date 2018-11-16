package structure;

public class PrintExpr extends Expr{

    private final SeqExpr sequence;

    public PrintExpr(SeqExpr sequence) {
        this.sequence = sequence;
    }

    @Override
    Val eval(Env env) {
        return null;
    }
}
