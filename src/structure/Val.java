package structure;

public abstract class Val extends Expr {
    @Override
    Val eval(Env env) {
        return this;
    }
}
