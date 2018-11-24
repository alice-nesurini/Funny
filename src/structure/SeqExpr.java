package structure;

import java.util.List;

// Adaptive pattern = wrapper class
public class SeqExpr extends Expr{
    private final ExprList exprs;

    public SeqExpr(ExprList exprs) {
        this.exprs=exprs;
    }

    //TODO: public because of testing
    public int size(){
        return exprs.getExprs().size();
    }
/*
    public List<Expr> getExprs(){
        return exprs;
    }*/

    @Override
    public Val eval(Env env) throws InterpreterException {
        Val val=null;
        for(int i=0; i<exprs.getExprs().size(); i++) {
            val=exprs.getExprs().get(i).eval(env);
        }
        return val;
    }
/*
    // TODO: I don't think I should do this add
    public void add(SeqExpr sequence) {
        exprs.add(sequence);
    }*/
}
