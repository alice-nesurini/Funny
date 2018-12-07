package structure;

// TODO: complete refactoring

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

    @Override
    public Val eval(Env env) throws InterpreterException {
        Val val=null;
        for(int i=0; i<exprs.getExprs().size(); i++) {
            val=exprs.getExprs().get(i).eval(env);
        }
        return val;
    }
}
