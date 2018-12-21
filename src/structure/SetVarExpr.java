package structure;

import tokenizer.TokenType;

public class SetVarExpr extends Expr {

    private final String id;
    private final Expr value;
    private final TokenType op;

    public SetVarExpr(String id, Expr value, TokenType op) {
        this.id = id;
        this.value = value;
        this.op = op;
    }

    @Override
    public Val eval(Env env) throws InterpreterException {
        switch (op) {
            case EQUALS:
                return env.add(id, value.eval(env));
            case MINUS_EQUALS:
                return env.add(id, env.get(id).subtract(value.eval(env)));
            case PLUS_EQUALS:
                return env.add(id, env.get(id).add(value.eval(env)));
            case MODULE_EQUALS:
                return env.add(id, env.get(id).module(value.eval(env)));
            case STAR_EQUALS:
                return env.add(id, env.get(id).star(value.eval(env)));
            case DIVISION_EQUALS:
                return env.add(id, env.get(id).divide(value.eval(env)));
        }
        return NilVal.instance();
    }
}