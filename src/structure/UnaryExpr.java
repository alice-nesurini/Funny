package structure;

import tokenizer.TokenType;

public class UnaryExpr extends Expr {

    private final TokenType type;
    private final Expr unary;

    public UnaryExpr(TokenType type, Expr unary) {
        this.type=type;
        this.unary=unary;
    }

    @Override
    public Val eval(Env env) {
        return null;
    }
}
