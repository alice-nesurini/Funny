package structure;

public class ClosureVal extends Val {
    private final Env env;
    private final Expr expr;

    public ClosureVal(Env env, Expr expr) {
        this.env = env;
        this.expr = expr;
    }

    // apply
}
