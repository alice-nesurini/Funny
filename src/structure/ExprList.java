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
        exprs.forEach(e-> {
            try {
                list.add(e.eval(env));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        return list;
    }
}
