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
        }
        return null;
    }
}
