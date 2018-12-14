package structure;

// TODO: missing implementation

public class IfExpr extends Expr{
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
        Val ifCondition;
        if(invertedLogic){
            ifCondition=condition.eval(env).checkBool().negate();
        }
        else{
            ifCondition=condition.eval(env).checkBool();
        }

        if(ifCondition.checkBool().toBool()){
            return ifActions.eval(env);
        }
        else{
            return elseActions.eval(env);
        }
    }
}
