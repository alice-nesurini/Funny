package structure;

import tokenizer.TokenType;

public class PrintExpr extends Expr {

    private final ExprList sequence;
    private final TokenType type;

    public PrintExpr(ExprList sequence, TokenType type) {
        this.sequence = sequence;
        this.type = type;
    }

    @Override
    public Val eval(Env env) throws InterpreterException {
        for (int i = 0; i < sequence.getExprs().size(); i++) {
            System.out.print(sequence.getExprs().get(i).eval(env));
        }
        if (type == TokenType.PRINTLN) {
            // add empty line
            System.out.print(System.lineSeparator());
        }
        return NilVal.instance();
    }
}
