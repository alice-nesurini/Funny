package structure;

import java.util.ArrayList;
import java.util.List;

public class ExprList {

    private final List<Expr> exprs;

    public ExprList(List<Expr> exprs) {
        this.exprs = exprs;
    }

    List<Val> eval(Env env) {
        // TODO
        List<Val> list=new ArrayList<>();
        exprs.forEach(e->list.add(e.eval(env)));
        return list;
    }
}
