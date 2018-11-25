package structure;

public class InvokeExpr extends Expr {

    private final Expr expr;
    private final ExprList exprList;

    public InvokeExpr(Expr expr, ExprList exprList) {
        this.expr = expr;
        this.exprList = exprList;
    }

    @Override
    public Val eval(Env env) throws InterpreterException {
        return expr.eval(env).checkClosure().apply(exprList.eval(env));
    }
}
