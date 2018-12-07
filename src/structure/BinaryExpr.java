package structure;

import tokenizer.TokenType;

public class BinaryExpr extends Expr{

    private final Expr left;
    private final Expr right;
    private final TokenType operator;

    public BinaryExpr(Expr left, Expr right, TokenType operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Val eval(Env env) throws InterpreterException {
        switch (operator){
            case PLUS:
                return new NumVal((left.eval(env).checkNum()).getValue().add((right.eval(env).checkNum()).getValue()));
            case MINUS:
                return new NumVal((left.eval(env).checkNum()).getValue().subtract((right.eval(env).checkNum()).getValue()));
            case STAR:
                return new NumVal((left.eval(env).checkNum()).getValue().multiply((right.eval(env).checkNum()).getValue()));
            case DIVISION:
                return new NumVal((left.eval(env).checkNum()).getValue().divide((right.eval(env).checkNum()).getValue()));
            case LESS_EQUALS:
                return new BoolVal(left.eval(env).lessEquals(right.eval(env)));
            case LESS:
                return new BoolVal(left.eval(env).less(right.eval(env)));
            case GREATER:
                return new BoolVal(left.eval(env).greater(right.eval(env)));
            case GREATER_EQUALS:
                return new BoolVal(left.eval(env).greaterEquals(right.eval(env)));
            case MODULE:
                return new NumVal((left.eval(env).checkNum()).getValue().remainder((right.eval(env).checkNum()).getValue()));
            default:
                return NilVal.instance();
        }
    }
}
