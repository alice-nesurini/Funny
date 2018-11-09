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
    public Boolean getValue() {
        /*switch(operator){
            case OR:
                return ((BoolExpr)left).getValue()||((BoolExpr)right).getValue();
        }*/
        return null;
    }
}