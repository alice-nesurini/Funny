package structure;

import java.util.ArrayList;

public class SetVarExpr extends Expr {

    private final String id;
    private final Expr value;

    public SetVarExpr(String id, Expr value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public Val eval(Env env) throws InterpreterException {
        //ENV must increase
        if(env==null){
            env=new Env(new Frame(new ArrayList<>(), new ArrayList<>(), new ArrayList<>()), null);
        }
        return env.add(id, value.eval(env));
    }
}
