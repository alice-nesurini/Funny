package structure;

public abstract class Val extends Expr {
    @Override
    public Val eval(Env env) throws InterpreterException {
        return this;
    }

    public StringVal checkString() throws InterpreterException {
        throw new InterpreterException("Not a string");
    }

    public NumVal checkNum() throws InterpreterException {
        throw new InterpreterException("Not a number");
    }

    public ClosureVal checkClosure() throws InterpreterException {
        throw new InterpreterException("Not a closure");
    }

    public BoolVal checkBool() throws InterpreterException {
        throw new InterpreterException("Not a bool");
    }
}
