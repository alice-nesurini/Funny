package structure;

import java.util.List;

public class ExprList {

    private final List<Expr> exprs;

    public ExprList(List<Expr> exprs) {
        this.exprs = exprs;
    }

    List<Val> eval(Env env) {
        return null;
    }
}
