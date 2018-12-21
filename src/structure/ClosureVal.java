package structure;

import java.util.List;

public class ClosureVal extends Val {

    // Una closure mantiene una funzione e l'ambiente in cui la closure viene creata (cfr. FunExpr)
    private final Env env;
    private final FunExpr funExpr;

    public ClosureVal(Env env, FunExpr funExpr) {
        this.env = env;
        this.funExpr = funExpr;
    }

    // apply:
    // una closure è applicata a una lista di valori con lo scopo di eseguire (valutare) il
    // corpo della funzione (un nodo) e ritornarne il valore al chiamante
    Val apply(List<Val> argVals) throws InterpreterException {
        return funExpr.getBody().eval(new Env(new Frame(funExpr.getParams(), funExpr.getLocals(), argVals), env));
    }

    @Override
    public Val add(Val val){
        return NilVal.instance();
    }

    @Override
    public ClosureVal checkClosure(){
        return this;
    }
}
