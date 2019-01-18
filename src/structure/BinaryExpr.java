package structure;

import tokenizer.TokenType;

import java.math.RoundingMode;

public class BinaryExpr extends Expr {

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
        switch (operator) {
            case AND:
                BoolVal leftEval = left.eval(env).checkBool();
                if (leftEval.toBool()) {
                    // if true evalute right
                    return new BoolVal(leftEval.and(right.eval(env).checkBool()));
                }
                return new BoolVal(false);
            case OR:
                // if left is true result will be true
                leftEval = left.eval(env).checkBool();
                if (!leftEval.toBool()) {
                    // eval the right
                    return new BoolVal(leftEval.or(right.eval(env).checkBool()));
                }
                return new BoolVal(true);
            case PLUS:
                try {
                    return new NumVal((left.eval(env).checkNum()).getValue().add((right.eval(env).checkNum()).getValue()));
                } catch (InterpreterException e) {
                    return left.eval(env).add(right.eval(env));
                }
            case MINUS:
                return new NumVal((left.eval(env).checkNum()).getValue().subtract((right.eval(env).checkNum()).getValue()));
            case STAR:
                return new NumVal((left.eval(env).checkNum()).getValue().multiply((right.eval(env).checkNum()).getValue()));
            case DIVISION:
                // try exact division
                // if exact quotient cannot be represented, ArithmeticException is thrown
                try {
                    return new NumVal((left.eval(env).checkNum()).getValue().divide((right.eval(env).checkNum()).getValue()));
                } catch (ArithmeticException e) {
                    return new NumVal((left.eval(env).checkNum()).getValue().divide((right.eval(env).checkNum()).getValue(), 100, RoundingMode.HALF_EVEN));
                }
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
            case COMPARISON:
                return new BoolVal(left.eval(env).comparison(right.eval(env)));
            case DIFFERENCE:
                return new BoolVal(left.eval(env).difference(right.eval(env)));
            default:
                return NilVal.instance();
        }
    }
}
