package structure;

public abstract class Val extends Expr {
    @Override
    public Val eval(Env env) {
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

    public boolean less(Val val) throws InterpreterException {
        throw new InterpreterException("Not correct classes [less method]");
    }

    public boolean greater(Val val) throws InterpreterException {
        throw new InterpreterException("Not correct classes [greater method]");
    }

    public boolean lessEquals(Val val) throws InterpreterException {
        throw new InterpreterException("Not correct classes [lessEquals method]");
    }

    public boolean greaterEquals(Val val) throws InterpreterException {
        throw new InterpreterException("Not correct classes [greaterEquals method]");
    }
}
