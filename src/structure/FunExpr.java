package structure;

import java.util.List;

public class FunExpr extends Expr {

    // Mantiene una lista di identificatori per i parametri,
    // una per le variabili locali e il nodo che costituisce il corpo della funzione
    private final List<String> params;
    private final List<String> locals;
    private final Expr body;

    public FunExpr(List<String> params, List<String> locals, Expr body) {
        this.params = params;
        this.locals = locals;
        this.body = body;
    }

    @Override
    public Val eval(Env env) {
        return new ClosureVal(env, this);
    }

    public List<String> getParams() {
        return params;
    }

    public List<String> getLocals() {
        return locals;
    }

    public Expr getBody() {
        return body;
    }
}
