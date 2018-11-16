package structure;

public class WhileExpr extends Expr{

    private final boolean invertedLogic;
    private final Expr whileCondition;
    private final Expr doActions;

    public WhileExpr(boolean invertedLogic, Expr whileCondition, Expr doActions) {
        this.invertedLogic = invertedLogic;
        this.whileCondition = whileCondition;
        this.doActions = doActions;
    }

    @Override
    Val eval(Env env) {
        return null;
    }
}
