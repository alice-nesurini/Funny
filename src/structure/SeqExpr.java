package structure;

import java.util.List;

// Adaptive pattern = wrapper class
public class SeqExpr extends Expr{
    private final List<Expr> exprs;

    public SeqExpr(List<Expr> exprs) {
        this.exprs=exprs;
    }

    //TODO: public because of testing
    public int size(){
        return exprs.size();
    }

    public List<Expr> getExprs(){
        return exprs;
    }

    @Override
    public Val eval(Env env) throws InterpreterException {
        for(int i=0; i<exprs.size(); i++) {
            exprs.get(i).eval(env);
        }
        return NilVal.instance();
    }

    // TODO: I don't think I should do this add
    public void add(SeqExpr sequence) {
        exprs.add(sequence);
    }
}
