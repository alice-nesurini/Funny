package structure;

public class LoopExpr extends Expr{

    private final boolean invertedLogic;
    private final Expr whileCondition;
    private final Expr doActions;

    public LoopExpr(boolean invertedLogic, Expr whileCondition, Expr doActions) {
        this.invertedLogic = invertedLogic;
        this.whileCondition = whileCondition;
        this.doActions = doActions;
    }

    @Override
    public <T> T getValue() {
        return null;
    }
}
