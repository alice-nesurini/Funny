package structure;

public class IfExpr extends Expr {
    private final boolean invertedLogic;
    private final Expr condition;
    private final Expr ifActions;
    private final Expr elseActions;

    public IfExpr(boolean invertedLogic, Expr condition, Expr ifActions, Expr elseActions) {
        this.invertedLogic = invertedLogic;
        this.condition = condition;
        this.ifActions = ifActions;
        this.elseActions = elseActions;
    }

    @Override
    public Val eval(Env env) throws InterpreterException {

        // (check if not inverted logic)
        if (!invertedLogic && condition.eval(env).checkBool().toBool()) {
            if (ifActions == null) {
                return NilVal.instance();
            }
            return ifActions.eval(env);
        } else {
            if (elseActions == null) {
                return NilVal.instance();
            }
            return elseActions.eval(env);
        }
    }
}
