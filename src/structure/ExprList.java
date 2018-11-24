package structure;

import java.util.ArrayList;
import java.util.List;

// ExprList non è un sottotipo di Expr e che il suo eval(Env) torna una lista di valori
public class ExprList {

    // ExprList che non mantiene altro che una lista di Expr
    private final List<Expr> exprs;

    public ExprList(List<Expr> exprs) {
        this.exprs = exprs;
    }

    List<Val> eval(Env env) throws InterpreterException {
        List<Val> list=new ArrayList<>();
        for(int i=0; i<exprs.size(); i++){
            list.add(exprs.get(i).eval(env));
        }
        return list;
    }

    public List<Expr> getExprs(){
        return exprs;
    }
}
