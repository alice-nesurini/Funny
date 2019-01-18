package structure;

// Adaptive pattern = wrapper class
public class SeqExpr extends Expr{
    private final ExprList exprs;

    public SeqExpr(ExprList exprs) {
        this.exprs=exprs;
    }

    @Override
    public Val eval(Env env) throws InterpreterException {
        Val val=NilVal.instance();
        for(int i=0; i<exprs.getExprs().size(); i++) {
            val=exprs.getExprs().get(i).eval(env);
        }
        return val;
    }
}
