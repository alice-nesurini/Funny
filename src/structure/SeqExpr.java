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
    Val eval(Env env) {
        return null;
    }
}
