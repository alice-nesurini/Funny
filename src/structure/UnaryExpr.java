package structure;

import tokenizer.TokenType;

public class UnaryExpr extends Expr {

    private final TokenType type;
    private final Expr unary;

    public UnaryExpr(TokenType type, Expr unary) {
        this.type = type;
        this.unary = unary;
    }

    @Override
    public Val eval(Env env) throws InterpreterException {
        switch (type) {
            case MINUS:
                return unary.eval(env).checkNum().negate();
            case PLUS:
                return unary.eval(env).checkNum().plus();
            case NOT:
                return unary.eval(env).checkBool().negate();
            default:
                return NilVal.instance();
        }
    }
}
