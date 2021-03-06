package structure;

public class GetVarExpr extends Expr {

    private final String variable;

    public GetVarExpr(String variable) {
        this.variable = variable;
    }

    @Override
    public Val eval(Env env) {
        return env.get(variable);
    }
}
