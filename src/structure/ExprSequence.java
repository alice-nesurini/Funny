package structure;

import java.util.List;

public class ExprSequence extends  Expr{
    private final List<Expr> exprs;

    public ExprSequence(List<Expr> exprs) {
        this.exprs=exprs;
    }

    //public because of testing
    public int size(){
        return exprs.size();
    }

    public List<Expr> getExprs(){
        return exprs;
    }

    public void print(){
        for(Expr e:getExprs()){
            //TODO: explicit cast just num implemented
            System.out.println("Expr : "+e.getValue());
        }
    }

    @Override
    public <T> T getValue() {
        return null;
    }
}
