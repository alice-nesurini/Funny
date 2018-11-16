package structure;

public abstract class Expr {
    abstract Val eval(Env env);
}
