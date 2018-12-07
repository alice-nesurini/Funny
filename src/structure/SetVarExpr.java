package structure;

public class SetVarExpr extends Expr {

    private final String id;
    private final Expr value;

    public SetVarExpr(String id, Expr value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public Val eval(Env env) throws InterpreterException {
        return env.add(id, value.eval(env));
    }
}