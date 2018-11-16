package structure;

import java.util.List;

public class SeqExpr extends Expr{
    private final List<Expr> exprs;

    public SeqExpr(List<Expr> exprs) {
        this.exprs=exprs;
    }

    //public because of testing
    public int size(){
        return exprs.size();
    }

    public List<Expr> getExprs(){
        return exprs;
    }

    @Override
    public Val eval(Env env) {
        exprs.forEach(e->e.eval(env));
        return null;
    }

    // added
    public void add(SeqExpr sequence) {
        exprs.add(sequence);
    }
}
